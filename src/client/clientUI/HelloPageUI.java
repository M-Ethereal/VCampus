package client.clientUI;

import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class HelloPageUI {

    private static ArrayList<EInfo> EInfo_list = new ArrayList<EInfo>();
    private static ArrayList<SchoolNews> SchoolNews_list = new ArrayList<SchoolNews>();

    //教务信息对象
    public class EInfo{
        public String Title;
        public String Href;
    }

    //学校信息对象
    public class SchoolNews{
        public String Title;
        public String Href;
    }

    //天气对象
    public class weather{
        public String Temperature;
        public String Situation;

    }


    public AnchorPane HelloPage() {
        //网站通知爬虫（学校新闻和教务信息）

        //教务信息
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                                                          @Override
                                                          public boolean verify(String hostname, SSLSession session) {
                                                              return true;
                                                          }
                                                      }
        );

        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers1() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }
            } }, new SecureRandom());
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        String EInfoURL = "https://jwc.seu.edu.cn/jwxx/list.htm";
        Document EInfoDoc = null;
        try {
            EInfoDoc = Jsoup.connect(EInfoURL).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements EInfoEle = EInfoDoc.getElementsByTag("a");
        Iterator EInfoIt = EInfoEle.iterator();
        String InfoURL = "https://jwc.seu.edu.cn";
        Element EInfoElement = null;
        while(EInfoIt.hasNext()) {
            EInfoElement = (Element)EInfoIt.next();
            if(EInfoElement.attr("target").toString().equals("_blank") && (EInfoElement.attr("title").toString().length()>3)) {
                EInfo einfo = new EInfo();
                einfo.Href = InfoURL + EInfoElement.attr("href");
                einfo.Title = EInfoElement.attr("title");
                EInfo_list.add(einfo);
            }
        }

        //学校新闻
        String SchoolNewsURL = "https://news.seu.edu.cn/5486/list.htm";
        Document SchoolNewsDoc = null;
        try {
            SchoolNewsDoc = Jsoup.connect(SchoolNewsURL).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements SchoolNewsEle = SchoolNewsDoc.getElementsByTag("a");
        Iterator SchoolNewsIt = SchoolNewsEle.iterator();
        String NewsURL = "https://news.seu.edu.cn";
        Element SchoolNewsElement = null;
        while(SchoolNewsIt.hasNext()) {
            SchoolNewsElement = (Element)SchoolNewsIt.next();
            if(SchoolNewsElement.attr("target").toString().equals("_blank") && (SchoolNewsElement.attr("title").toString().length()>3)) {
                SchoolNews sn = new SchoolNews();
                sn.Href = NewsURL + SchoolNewsElement.attr("href");
                sn.Title = SchoolNewsElement.attr("title");
                SchoolNews_list.add(sn);
            }
        }

        //教务信息
        JFXListView<Label> einfo_list = new JFXListView<Label>();
        einfo_list.setPrefSize(370, 220);
        for(int i = 0 ; i < EInfo_list.size() ; i++) {
            EInfo e = EInfo_list.get(i);
            Label l =new Label(e.Title);
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(e.Href));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            einfo_list.getItems().add(l);

        }
        einfo_list.getStyleClass().add("mylistview");

        ImageView einfo_img = new ImageView("/client/Image/helloPage_info.png");
        einfo_img.setPreserveRatio(true);
        einfo_img.setFitHeight(130);
        einfo_img.setLayoutY(240);

        einfo_list.setLayoutX(135);
        einfo_list.setLayoutY(300);

        //学校要闻
        JFXListView<Label> schoolnews_list = new JFXListView<Label>();
        schoolnews_list.setPrefSize(380, 220);
        for(int i = 0 ; i < SchoolNews_list.size() ; i++) {
            if(i>0 && SchoolNews_list.get(i).Title.equals(SchoolNews_list.get(i-1).Title)) {
                continue;
            }
            SchoolNews e = SchoolNews_list.get(i);
            Label l =new Label(e.Title);
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        java.awt.Desktop.getDesktop().browse(new URI(e.Href));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            schoolnews_list.getItems().add(l);

        }
        schoolnews_list.getStyleClass().add("mylistview");

        ImageView schoolnews_img = new ImageView("/client/Image/helloPage_schoolNews.png");
        schoolnews_img.setPreserveRatio(true);
        schoolnews_img.setFitHeight(130);
        schoolnews_img.setLayoutY(240);
        schoolnews_img.setLayoutX(520);

        schoolnews_list.setLayoutX(655);
        schoolnews_list.setLayoutY(300);


        //使用Jsoup获取天气
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.nmc.cn/publish/forecast/AJS/nanjing.html").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements ele = doc.getElementsByClass("weatherWrap");
        Iterator it = ele.iterator();
        Element element = null;
        while(it.hasNext()) {
            element = (Element)it.next();
            Node titleNode= element.child(0).childNode(0);
            SimpleDateFormat df = new SimpleDateFormat("\nMM/dd");//设置日期格式
            if(df.format(new Date()).toString().equals(titleNode.toString())) {
                break;
            }
        }
        weather w = new weather();
        w.Temperature=element.getElementsByClass("tmp tmp_lte_20").text();
        w.Situation=element.getElementsByClass("desc").text();
        if(w.Situation.length()>2)
            w.Situation = w.Situation.substring(0, 2);

        String ImageURL= "/client/Image/";

        SimpleDateFormat df1 = new SimpleDateFormat(" yyyy 年"+"  MM 月"+"  dd 日");//设置日期格式
        Label time = new Label("今天是"+df1.format(new Date()));// new Date()为获取当前系统时间
        time.setFont(new Font(20));
        time.setLayoutX(250);
        time.setLayoutY(50);
        Label we = new Label("今日天气： "+w.Situation+" "+w.Temperature);
        we.setLayoutX(250);
        we.setLayoutY(105);
        we.setFont(new Font(20));

        Label notice = new Label("今天也要元气满满嗷(ง •_•)ง");
        notice.setLayoutY(160);
        notice.setLayoutX(250);
        notice.setFont(new Font(20));

        SimpleDateFormat df2 = new SimpleDateFormat("HH");
        if(Integer.parseInt(df2.format(new Date()))>20 || Integer.parseInt(df2.format(new Date()))<6) {
            ImageURL = ImageURL + "night.png";
            notice.setText("夜深啦，今天就早点休息吧~");
        }
        else if(w.Situation.contains("雨"))
            ImageURL = ImageURL + "rainy.png";
        else if(w.Situation.contains("晴"))
            ImageURL = ImageURL + "sunny.png";
        else if(w.Situation.contains("雪"))
            ImageURL = ImageURL + "snowy.png";
        else if(w.Situation.contains("云"))
            ImageURL = ImageURL + "cloudy.png";
        else if(w.Situation.contains("雷"))
            ImageURL = ImageURL + "lightning.png";
        else
            ImageURL = ImageURL + "sunny.png";

        ImageView wea = new ImageView(ImageURL);
        wea.setPreserveRatio(true);
        wea.setFitWidth(200);
        wea.setLayoutX(40);
        wea.setLayoutY(20);





        AnchorPane ap = new AnchorPane();

        ap.getChildren().addAll(einfo_img,einfo_list,schoolnews_img,schoolnews_list,time,we,wea,notice);
//        ap.getChildren().addAll(einfo_img,einfo_list,schoolnews_img,schoolnews_list);

        return ap;
    }
}
