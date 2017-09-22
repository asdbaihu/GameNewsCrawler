package spider;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/20
 */
public class TestLoopFactor {
    public static void main(String[] args) {
        int max = 0;
        int factor = 1;
        int val = 60;
        int result = 0;
        for (int i = 1; i < val; i++) {
            int count = 0;
            for(int j = 1; ; j++){
                if(val%((i*j)%val) == 0) {
                    if(count > max){
                        max = count;
                        factor = i;
                        result = j;
                    }
                    break;
                }else{
                    count++;
                }

            }
        }
        System.out.println(max + " " + factor + " " + result);
    }
}
