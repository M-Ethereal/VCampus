package vo;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 50000;
    private String BookID;
    private String BookName;
    private String BookAuthor;
    private boolean BookSite;
    private boolean BookStatus;
    private String BookTag;
    private int BookStar;
    private String BookPublisher;
    private String BookPubDate;
    private String BookSearchIndex;
    public void setBookID(String ID)
    {
        BookID=ID;
    }
    public void setBookName(String name)
    {
        BookName=name;
    }
    public void setBookAuthor(String author)
    {
        BookAuthor=author;
    }
    public void setBookSite(boolean site)
    {
        BookSite=site;
    }
    public void setBookStatus(boolean status)
    {
        BookStatus=status;
    }
    public void setBookTag(String tag)
    {
        BookTag=tag;
    }
    public void setBookStar(int star)
    {
        BookStar=star;
    }
    public void setBookPublisher(String publisher)
    {
        BookPublisher=publisher;
    }
    public void setBookPubDate(String pubDate)
    {
        BookPubDate=pubDate;
    }
    public void setBookSearchIndex(String index)
    {
        BookSearchIndex=index;
    }
    public String getBookID()
    {
        return BookID;
    }
    public String getBookName()
    {
        return BookName;
    }
    public String getBookAuthor()
    {
        return BookAuthor;
    }
    public boolean getBookSite()
    {
        return BookSite;
    }
    public boolean getBookStatus()
    {
        return BookStatus;
    }
    public String getBookTag()
    {
        return BookTag;
    }
    public int getBookStar()
    {
        return BookStar;
    }
    public String getBookPublisher()
    {
        return BookPublisher;
    }
    public String getBookPubDate()
    {
        return BookPubDate;
    }
    public String getBookSearchIndex()
    {
        return BookSearchIndex;
    }
    public Book()//无参构造函数
    {
        this.BookID=" ";
        this.BookName=" ";
        this.BookAuthor=" ";
        this.BookSite=false;
        this.BookStatus=false;
        this.BookTag=" ";
        this.BookStar=0;
        this.BookPublisher=" ";
        this.BookPubDate=" ";
        this.BookSearchIndex=" ";
    }
    public Book(String BookID,String BookName,String BookAuthor,boolean BookSite,//有参构造函数
                   boolean BookStatus,String BookTag,int BookStar,String BookPublisher,String BookPubDate)
    {
        this.BookID=BookID;
        this.BookName=BookName;
        this.BookAuthor=BookAuthor;
        this.BookSite=BookSite;
        this.BookStatus=BookStatus;
        this.BookTag=BookTag;
        this.BookStar=BookStar;
        this.BookPublisher=BookPublisher;
        this.BookPubDate=BookPubDate;
        this.BookSearchIndex=BookID+","+BookName+","+BookAuthor+","+String.valueOf(BookSite)+","+String.valueOf(BookStatus)
                +","+BookTag+","+BookPublisher+","+BookPubDate;
    }
}
