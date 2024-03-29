package tv.duojiao.dao;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tv.duojiao.model.async.Task;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.model.rec.RecommendEnity;
import tv.duojiao.utils.spider.PageExtractor;

import java.io.OutputStream;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * CommonWebpageDAO
 *
 * @author Yodes
 */
@Component
public class CommonWebpageDAO extends IDAO<Webpage> {
    private final static String INDEX_NAME = "commons", TYPE_NAME = "webpage";
    private static final int SCROLL_TIMEOUT = 1;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
            .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime()))
            .setDateFormat(DateFormat.LONG).create();
    private Logger LOG = LogManager.getLogger(CommonWebpageDAO.class);

    @Autowired
    public CommonWebpageDAO(@Qualifier("getEsClient") ESClient esClient) {
        super(esClient, INDEX_NAME, TYPE_NAME);
    }

    @Override
    public String index(Webpage webpage) {
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.prepareIndex(INDEX_NAME, TYPE_NAME)
                    .setSource(gson.toJson(webpage))
                    .get();
            return indexResponse.getId();
        } catch (Exception e) {
            LOG.error("索引 Webpage 出错," + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected boolean check() {
        return esClient.checkCommonsIndex() && esClient.checkWebpageType();
    }

    /**
     * 根据uuid获取结果
     *
     * @param uuid 爬虫uuid
     * @param size 每页数量
     * @param page 页码
     * @return
     */
    public List<Webpage> getWebpageBySpiderUUID(String uuid, int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchQuery("spiderUUID", uuid))
                .setSize(size).setFrom(size * (page - 1));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 导出 标题-正文 对
     *
     * @param queryBuilder 查询
     * @param outputStream 文件输出流
     */
    private void exportTitleContentPairBy(QueryBuilder queryBuilder, OutputStream outputStream) {
        exportData(queryBuilder,
                searchResponse -> {
                    List<List<String>> resultList = Lists.newLinkedList();
                    List<Webpage> webpageList = warpHits2List(searchResponse.getHits());
                    webpageList.forEach(webpage -> resultList.add(Lists.newArrayList(webpage.getTitle())));
                    return resultList;
                },
                searchResponse -> {
                    List<String> resultList = Lists.newLinkedList();
                    List<Webpage> webpageList = warpHits2List(searchResponse.getHits());
                    webpageList.forEach(webpage -> resultList.add(webpage.getContent()));
                    return resultList;
                }, outputStream);
    }

    /**
     * 根据爬虫id导出 标题-正文 对
     *
     * @param uuid         爬虫id
     * @param outputStream 文件输出流
     */
    public void exportTitleContentPairBySpiderUUID(String uuid, OutputStream outputStream) {
        exportTitleContentPairBy(QueryBuilders.matchQuery("spiderUUID", uuid).operator(Operator.AND), outputStream);
    }

    /**
     * 导出 webpage的JSON对象
     *
     * @param queryBuilder 查询
     * @param includeRaw   是否包含网页快照
     * @param outputStream 文件输出流
     */
    private void exportWebpageJSONBy(QueryBuilder queryBuilder, Boolean includeRaw, OutputStream outputStream) {
        exportData(queryBuilder,
                searchResponse -> Lists.newLinkedList(),
                searchResponse -> {
                    List<String> resultList = Lists.newLinkedList();
                    List<Webpage> webpageList = warpHits2List(searchResponse.getHits());
                    webpageList.forEach(webpage -> resultList.add(gson.toJson(includeRaw ? webpage : webpage.setRawHTML(null))));
                    return resultList;
                }, outputStream);
    }

    /**
     * 根据爬虫id导出 webpage的JSON对象
     *
     * @param uuid         爬虫id
     * @param includeRaw   是否包含网页快照
     * @param outputStream 文件输出流
     */
    public void exportWebpageJSONBySpiderUUID(String uuid, Boolean includeRaw, OutputStream outputStream) {
        exportWebpageJSONBy(QueryBuilders.matchQuery("spiderUUID", uuid).operator(Operator.AND), includeRaw, outputStream);
    }

    /**
     * 根据domain导出 webpage的JSON对象
     *
     * @param domain       域名
     * @param includeRaw   是否包含网页快照
     * @param outputStream 文件输出流
     */
    public void exportWebpageJSONByDomain(String domain, Boolean includeRaw, OutputStream outputStream) {
        exportWebpageJSONBy(QueryBuilders.matchQuery("domain", domain).operator(Operator.AND), includeRaw, outputStream);
    }

    /**
     * 根据ES中的id获取网页
     *
     * @param id 网页id
     * @return
     */
    public Webpage getWebpageById(String id) {
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, id).get();
        Preconditions.checkArgument(response.isExists(), "无法找到ID为%s的文章,请检查参数", id);
        return warpHits2Info(response.getSourceAsString(), id);
    }

    public String getNewsInfoById(String id) {
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, id).get();
        Preconditions.checkArgument(response.isExists(), "无法找到ID为%s的文章,请检查参数", id);
        return response.getSourceAsString();
    }

    /**
     * 根据id删除网页
     *
     * @param id 网页id
     * @return 是否删除
     */
    public boolean deleteById(String id) {
        DeleteResponse response = client.prepareDelete(INDEX_NAME, TYPE_NAME, id).get();
        return response.getResult() == DeleteResponse.Result.DELETED;
    }

    /**
     * 根据domain获取结果
     *
     * @param domain 网站域名
     * @param size   每页数量
     * @param page   页码
     * @return
     */
    public List<Webpage> getWebpageByDomain(String domain, String sortKey, String order, int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchQuery("domain", domain))
                .setSize(size).setFrom(size * (page - 1));

        // 增加自定义搜索排序规则-Yodes, 暂时不知道何用
        if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(order)) {
            searchRequestBuilder.addSort(sortKey, "升序".equals(order) ? SortOrder.ASC : SortOrder.DESC);
        }
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 根据domain列表获取结果
     *
     * @param domain 网站域名列表
     * @param size   每页数量
     * @param page   页码
     * @return
     */
    public List<Webpage> getWebpageByDomains(Collection<String> domain, int size, int page) {
        if (domain.size() == 0) {
            return Lists.newArrayList();
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        domain.forEach(s -> boolQueryBuilder.should(QueryBuilders.matchQuery("domain", s)));
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(boolQueryBuilder)
                .addSort("gatherTime", SortOrder.DESC)
                .setSize(size).setFrom(size * (page - 1));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return warpHits2List(response.getHits());
    }

    private Webpage warpHits2Info(SearchHit hit) {
        Webpage webpage = gson.fromJson(hit.getSourceAsString(), Webpage.class);
        webpage.setId(hit.getId());
        return webpage;
    }

    private RecommendEnity wrapHits2Info2(SearchHit hit) {
        RecommendEnity recommendEnitys = gson.fromJson(hit.getSourceAsString(), RecommendEnity.class);
        recommendEnitys.setId(hit.getId());
        return recommendEnitys;
    }

    public Webpage warpHits2Info(String jsonSource, String id) {
        Webpage webpage = gson.fromJson(jsonSource, Webpage.class);
        webpage.setId(id);
        return webpage;
    }

    private List<Webpage> warpHits2List(SearchHits hits) {
        List<Webpage> webpageList = Lists.newLinkedList();
        hits.forEach(searchHitFields -> {
            webpageList.add(warpHits2Info(searchHitFields));
        });
        return webpageList;
    }

    private List<RecommendEnity> wrapHits2List2(SearchHits hits) {
        List<RecommendEnity> recommendEnities = Lists.newLinkedList();
        hits.forEach(searchHitFields -> {
            recommendEnities.add(wrapHits2Info2(searchHitFields));
        });
        return recommendEnities;
    }

    /**
     * 搜索es库中文章
     *
     * @param query 关键词
     * @param size  页面容量
     * @param page  页码
     * @return
     */
    public List<Webpage> searchByQuery(String query, int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.queryStringQuery(query).analyzer("query_ansj").defaultField("content"))
                .setSize(size).setFrom(size * (page - 1));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 列出库中所有文章,并按照抓取时间排序
     *
     * @param size 页面容量
     * @param page 页码
     * @return
     */
    public List<Webpage> listAll(int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchAllQuery())
                .addSort("gatherTime", SortOrder.DESC)
                .setSize(size).setFrom(size * (page - 1));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 开始滚动数据
     *
     * @return 滚动id
     */
    public Pair<String, List<Webpage>> startScroll() {
        return startScroll(QueryBuilders.matchAllQuery(), 50);
    }

    /**
     * 开始滚动数据
     *
     * @param queryBuilder 查询句柄
     * @return 滚动id和当前的一批数据
     */
    public Pair<String, List<Webpage>> startScroll(QueryBuilder queryBuilder, int size) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(queryBuilder)
                .setSize(size)
                .setScroll(TimeValue.timeValueMinutes(SCROLL_TIMEOUT));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return new MutablePair<>(response.getScrollId(), warpHits2List(response.getHits()));
    }

    /**
     * 更新网页
     *
     * @param webpage 网页
     * @return
     */
    public boolean update(Webpage webpage) throws ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX_NAME);
        updateRequest.type(TYPE_NAME);
        updateRequest.id(webpage.getId());
        updateRequest.doc(gson.toJson(webpage));
        UpdateResponse response = client.update(updateRequest).get();
        return response.getResult() == UpdateResponse.Result.UPDATED;
    }

    /**
     * 获取query的关联信息
     *
     * @param query 查询queryString
     * @param size  结果集数量
     * @return 相关信息
     */
    public Pair<Map<String, List<Terms.Bucket>>, List<Webpage>> relatedInfo(String query, int size) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.queryStringQuery(query))
                .addSort("gatherTime", SortOrder.DESC)
                .addAggregation(AggregationBuilders.terms("relatedPeople").field("namedEntity.nr"))
                .addAggregation(AggregationBuilders.terms("relatedLocation").field("namedEntity.ns"))
                .addAggregation(AggregationBuilders.terms("relatedInstitution").field("namedEntity.nt"))
                .addAggregation(AggregationBuilders.terms("relatedKeywords").field("keywords"))
                .setSize(size);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Map<String, List<Terms.Bucket>> info = Maps.newHashMap();
        info.put("relatedPeople", ((Terms) response.getAggregations().get("relatedPeople")).getBuckets());
        info.put("relatedLocation", ((Terms) response.getAggregations().get("relatedLocation")).getBuckets());
        info.put("relatedInstitution", ((Terms) response.getAggregations().get("relatedInstitution")).getBuckets());
        info.put("relatedKeywords", ((Terms) response.getAggregations().get("relatedKeywords")).getBuckets());
        return Pair.of(info, warpHits2List(response.getHits()));
    }

    /**
     * 批量更新网页
     *
     * @param webpageList 网页列表
     * @return
     */
    public boolean update(List<Webpage> webpageList) throws ExecutionException, InterruptedException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (Webpage webpage : webpageList) {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(INDEX_NAME);
            updateRequest.type(TYPE_NAME);
            updateRequest.id(webpage.getId());
            updateRequest.doc(gson.toJson(webpage));
            bulkRequest.add(updateRequest);
        }
        BulkResponse bulkResponse = bulkRequest.get();
        return bulkResponse.hasFailures();
    }

    /**
     * 根据scrollId获取全部数据
     *
     * @param scrollId scrollId
     * @return 网页列表
     */
    public List<Webpage> scrollAllWebpage(String scrollId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(scrollId), "scrollId不可为空");
        SearchResponse response = client.prepareSearchScroll(scrollId)
                .setScroll(TimeValue.timeValueMinutes(SCROLL_TIMEOUT))
                .execute().actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 统计每个网站有多少文章
     *
     * @return 域名-文章数
     */
    public Map<String, Long> countDomain(int size) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("domain").field("domain").size(size).order(Terms.Order.count(false)));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Terms termsAgg = response.getAggregations().get("domain");
        List<Terms.Bucket> list = termsAgg.getBuckets();
        Map<String, Long> count = Maps.newLinkedHashMap();
        list.stream().filter(bucket -> ((String) bucket.getKey()).length() > 1).forEach(bucket -> {
            count.put((String) bucket.getKey(), bucket.getDocCount());
        });
        return count;
    }

    /**
     * 统计指定域名的网站的文章的词频
     *
     * @param domain 网站域名
     * @return 词-词频
     */
    public Map<String, Long> countWordByDomain(String domain) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchQuery("domain", domain))
                .addAggregation(AggregationBuilders.terms("content").field("content").size(200));
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Terms termsAgg = response.getAggregations().get("content");
        List<Terms.Bucket> list = termsAgg.getBuckets();
        Map<String, Long> count = new HashMap<>();
        list.stream().filter(bucket -> ((String) bucket.getKey()).length() > 1).forEach(bucket -> {
            count.put((String) bucket.getKey(), bucket.getDocCount());
        });
        return count;
    }

    /**
     * 根据网站的文章ID获取相似网站的文章
     *
     * @param id   文章ID
     * @param size 页面容量
     * @param page 页码
     * @return
     */
    public List<Webpage> moreLikeThis(String id, int size, int page) {
        MoreLikeThisQueryBuilder.Item[] items = {new MoreLikeThisQueryBuilder.Item(INDEX_NAME, TYPE_NAME, id)};
        String[] fileds = {"title", "staticFields.GameCategory.keyword", "content"};
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(moreLikeThisQuery(fileds, null, items)
                        .minTermFreq(1)
                        .maxQueryTerms(12))
                .setSize(size).setFrom(size * (page - 1))
                .execute()
                .actionGet();
        return warpHits2List(response.getHits());
    }

    /**
     * 统计指定网站每天抓取数量
     *
     * @param domain 网站域名
     * @return
     */
    public Map<String, Long> countDomainByGatherTime(String domain, int days) {
        AggregationBuilder aggregation =
                AggregationBuilders
                        .dateHistogram("agg")
                        .field("gatherTime")
                        .dateHistogramInterval(DateHistogramInterval.DAY).order(Histogram.Order.KEY_ASC);
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(QueryBuilders.matchQuery("domain", domain))
                .addAggregation(aggregation)
                .setSize(days);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Histogram agg = response.getAggregations().get("agg");
        Map<String, Long> result = Maps.newHashMap();
        for (Histogram.Bucket entry : agg.getBuckets()) {
            DateTime key = (DateTime) entry.getKey();    // Key
            long docCount = entry.getDocCount();         // Doc count
            result.put(key.toString("yyyy-MM-dd"), docCount);

        }
        return result;
    }

    /**
     * 根据网站domain删除数据
     *
     * @param domain 网站域名
     * @param task   任务实体
     * @return 是否全部数据删除成功
     */
    public boolean deleteByDomain(String domain, Task task) {
        return deleteByQuery(QueryBuilders.matchQuery("domain", domain), task);
    }

    /**
     * 根据关键词和域名分页查询
     *
     * @param query
     * @param domain
     * @param size
     * @param page
     * @return
     */
    public Pair<List<Webpage>, Long> getWebpageByKeywordAndDomain(String query, String domain, String sortKey, String order, int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME);
        QueryBuilder keywordQuery, domainQuery;
        if (StringUtils.isBlank(query)) {
            query = "*";
        }
        Map<String, Float> weightQuery = new HashMap<String, Float>() {
            {
                put("content", 0.6f);
                put("title", 0.3f);
                put("staticFields.GameCategory", 0.8f);
            }
        };
        keywordQuery = QueryBuilders.queryStringQuery(query).analyzer("query_ansj").fields(weightQuery);
        if (StringUtils.isBlank(domain)) {
            domain = "*";
        } else {
            domain = "*" + domain + "*";
        }
        domainQuery = QueryBuilders.queryStringQuery(domain).field("domain");


        searchRequestBuilder
                .setQuery(keywordQuery)
                .setPostFilter(domainQuery)
                .setSize(size).setFrom(size * (page - 1));

        //增加按照自定义搜素排序规则-Yodes
        if (StringUtils.isNotBlank(sortKey) && StringUtils.isNotBlank(order)) {
            searchRequestBuilder.addSort(sortKey, "升序".equals(order) ? SortOrder.ASC : SortOrder.DESC);
        }
        SearchHits searchHits = searchRequestBuilder.get().getHits();
        return Pair.of(warpHits2List(searchHits), searchHits.getTotalHits());
    }

    /**
     * 搜索es库中文章---  游戏名+资讯分类
     *
     * @param query    关键词
     * @param gameName 游戏名称
     * @param category 资讯分类
     * @param size     页面容量
     * @param page     页码
     * @return
     */
    public List<RecommendEnity> searchByGame(String query, String gameName, String category, int size, int page) {
        Map<String, Float> weightField = new HashMap<String, Float>() {
            {
                put("content", 0.3f);
                put("title", 0.6f);
                put("staticFields.GameCategory.keyword", 0.8f);
            }
        };

        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(wildcardQuery("staticFields.GameCategory.keyword", "*" + gameName + "*"))
                .must(wildcardQuery("category.raw", "*" + category + "*"))
                .should(wildcardQuery("content", "*" + category + "*"))
                .should(QueryBuilders.queryStringQuery(query).analyzer("query_ansj").fields(weightField));
        String[] include = new String[]{
                "id", "title", "summary", "category", "publishTime", "imageList", "assistField"
        };
        String[] exclude = new String[]{
                ""
        };
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setQuery(queryBuilder)
                .setFetchSource(include, exclude)
                .setSize(size).setFrom(size * (page - 1))
                .addSort("publishTime", SortOrder.DESC);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return wrapHits2List2(response.getHits());
    }

    /**
     * 根据关键词及游戏列表进行推荐
     *
     * @param keyList
     * @param gameList
     * @param size
     * @param page
     * @return
     */
    public Pair<List<RecommendEnity>, Long> getWebpageByKeywords(String keyList, String gameList, int size, int page) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME);
        QueryBuilder keywordQuery, gameQuery, rangeQuery, postFilter;
        String query = keyList.trim(), game = gameList.trim();

        Map<String, Float> weightQuery = new HashMap<String, Float>() {
            {
                put("content", 0.7f);
                put("title", 1.5f);
                put("staticFields.GameCategory.keyword", 0.4f);
            }
        };
        keywordQuery = QueryBuilders
                .queryStringQuery(query.toString())
                .analyzer("query_ansj")
                .fields(weightQuery);

        gameQuery = QueryBuilders
                .queryStringQuery(game.toString())
                .analyzer("query_ansj")
                .field("staticFields.GameCategory.keyword");
        Date fDate = PageExtractor.getFrontDate(Calendar.getInstance().getTime(), "DAY", 5);
        Date tDate = Calendar.getInstance().getTime();
        rangeQuery = QueryBuilders
                .rangeQuery("publishTime")
                .from(fDate.getTime())
                .to(tDate.getTime())
                .includeLower(true)
                .includeUpper(true);
        postFilter = boolQuery()
                .should(gameQuery)
                .should(keywordQuery)
                .must(rangeQuery);
        String[] include = new String[]{
                "assistField", "id", "title", "summary", "category", "publishTime", "imageList"
        };
        String[] exclude = new String[]{
                ""
        };

        searchRequestBuilder
                .setQuery(postFilter)
                .setFetchSource(include, exclude)
                .setSize(size)
                .setFrom(size * (page - 1));

        //增加按照自定义搜素排序规则-Yodes
        SearchHits searchHits = searchRequestBuilder.get().getHits();
        return Pair.of(wrapHits2List2(searchHits), searchHits.getTotalHits());
    }
}
