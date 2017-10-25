package tv.duojiao.utils.spider;

import com.google.common.collect.Maps;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * NamedEntityExtractor
 *
 * @author Yodes
 */
@Component
public class HANLPExtractor implements NLPExtractor {
    private static Segment segment = HanLP.newSegment()
            .enableOrganizationRecognize(true).enablePlaceRecognize(true).enableAllNamedEntityRecognize(true);

    public HANLPExtractor() {
        segment = HanLP.newSegment()
                .enableOrganizationRecognize(true).enablePlaceRecognize(true).enableAllNamedEntityRecognize(true);
    }

    /**
     * 抽取命名实体
     *
     * @param content 文章正文
     * @return map的key是一下三种nr, ns, nt  其value就是对应的词表
     */
    @Override
    public Map<String, Set<String>> extractNamedEntity(String content) {
        if (segment == null) {
            segment = HanLP.newSegment().enableOrganizationRecognize(true).enablePlaceRecognize(true);
        }
        List<Term> termList = segment.seg(content);
        Set<String> nrList = termList.stream().filter(term -> term.nature.startsWith("nr"))
                .map(term -> term.word).collect(Collectors.toSet());
        Set<String> nsList = termList.stream().filter(term -> term.nature.startsWith("ns"))
                .map(term -> term.word).collect(Collectors.toSet());
        Set<String> ntList = termList.stream().filter(term -> term.nature.startsWith("nt"))
                .map(term -> term.word).collect(Collectors.toSet());
        Map<String, Set<String>> namedEntity = Maps.newHashMap();
        namedEntity.put("nr", nrList);
        namedEntity.put("ns", nsList);
        namedEntity.put("nt", ntList);
        return namedEntity;
    }

    /**
     * 抽取摘要
     *
     * @param content 文章正文
     * @return 摘要句子列表
     */
    @Override
    public List<String> extractSummary(String content) {
        return HanLP.extractSummary(content, 5);
    }

    /**
     * 抽取关键词
     *
     * @param content 文章正文
     * @return 关键词列表
     */
    @Override
    public List<String> extractKeywords(String content) {
        StringBuffer sb = new StringBuffer();
        List<String> keywordList = HanLP.extractKeyword(content, 20);
        List<String> summaryList = HanLP.extractSummary(content, 5);
        keywordList.forEach(s -> {
            sb.append(s + " ");
        });
        summaryList.forEach(s -> {
            sb.append(s + " ");
        });
        List<Term> resultList = segment.seg(sb.toString());

        for (Iterator<Term> it = resultList.iterator(); it.hasNext(); ) {
            Term tempKeyword = it.next();
            if (!CoreStopWordDictionary.shouldInclude(tempKeyword)) {
                it.remove();
            } else {
//                System.out.println(tempKeyword + "   " + tempKeyword.getFrequency());
            }
        }
        Set<String> tempSet = resultList.stream().filter(term ->
                term.nature.startsWith("n") || term.nature.startsWith("rr")
        ).map(term -> term.word).collect(Collectors.toSet());
        return new ArrayList<>(tempSet);
    }

    public List<String> sortByFrequency(List<Term> list, int size) {
        Map<Term, Integer> map = new HashMap<>();
        list.forEach(term -> {
            if (map.containsKey(term)) {
                map.put(term, map.get(term) + term.getFrequency());
            } else {
                map.put(term, term.getFrequency());
            }
        });
        int counter = 0;
        List<Term> tempList = new ArrayList<>();
        for (Map.Entry<Term, Integer> entry : map.entrySet()) {
            if (counter < size) {
                tempList.add(entry.getKey());
                System.out.println(entry.toString());
            } else {
                break;
            }
        }

        tempList.sort(new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return o1.getFrequency() - o2.getFrequency();
            }
        });

        return tempList.stream().filter(term ->
                term.nature.startsWith("n") || term.nature.startsWith("rr")
        ).map(term -> term.word).collect(Collectors.toList());
    }
}
