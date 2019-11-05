import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class Json {
    public static void main(String[] args){

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        jedis.auth("123456");

        Article article=new Article();
        article.setTitle("wl1701");
        article.setContent("zxh");

        save(jedis,article); //增加
        del(jedis,2);   //删除
        update(jedis,1);//修改
        find(jedis,1);  //查询


    }
    public static Long save(Jedis jedis,Article article){
        String articleJson= JSON.toJSONString(article);
        Long articleId=jedis.incr("article");
        jedis.set("article"+articleId+"Data",articleJson);
        System.out.println(articleJson);
        return articleId;
    }
    public static void del(Jedis jedis,Integer id){

        jedis.del("article"+id+"Data");
    }
    public static void update(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        article.setTitle("xxx");
        article.setContent("xxx");
        String post=JSON.toJSONString(article);
        jedis.set("article"+id+"Data",post);
        System.out.println(post);
    }
    public static Article find(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        return article;
    }
}
