import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Hash {

    public static void main(String args[]) {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("123456");
        Article article=new Article();
        article.setAuthor("zxh");
        article.setContent("sb");
        article.setTitle("sxas");
        article.setTime("1999");
        Long postId=savePost(article,jedis);
        System.out.println("保存成功");
        Article article1=getArticle(postId,jedis);
        System.out.println(article1);
        System.out.println("获取成功");

    }

    static Long savePost(Article article,Jedis jedis)
    {
        Long postId=jedis.incr("posts");
        Map<String,String> blog = new HashMap<String, String>();
        blog.put("title",article.getTitle());
        blog.put("author",article.getAuthor());
        blog.put("content",article.getContent());
        blog.put("time",article.getTime());
        jedis.hmset("post"+postId+":data",blog);
        return postId;
    }
    static Article getArticle( Long articleId,Jedis jedis) {
        Map<String,String> myBlog=jedis.hgetAll("article:"+articleId+":data");
        Article article=new Article();
        article.setTitle(myBlog.get("title"));
        article.setAuthor(myBlog.get("author"));
        article.setContent(myBlog.get("content"));
        article.setTime(myBlog.get("time"));
        return article;
    }

}
