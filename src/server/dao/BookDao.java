package server.dao;
import java.sql.*;
import java.util.ArrayList;

import vo.Book;

public class BookDao{
    private DbHelper access=new DbHelper();
    private PreparedStatement stmt=null;
    //private Statement sqll=null;
    private ResultSet res=null;
    //增完整的一条记录
    public  boolean Insert(Book lib)
    {
        BookDao BookDao=new BookDao();
        if(BookDao.SearchByBookID(lib.getBookID()).size()>0)
        {
            return false;
        }
        else
        {
            String bookSearchIndex = null;
            bookSearchIndex=lib.getBookID()+","+lib.getBookName()+","+lib.getBookAuthor()
                    +","+String.valueOf(lib.getBookSite())+","+
                    String.valueOf(lib.getBookStatus())+","+lib.getBookTag()
                    +","+lib.getBookPublisher()+","+lib.getBookPubDate();
            try
            {

                String sql="insert into Book values(?,?,?,?,?,?,?,?,?,?,?)";
                stmt=access.connection.prepareStatement(sql);
                stmt.setInt(1, 1);
                stmt.setString(2, lib.getBookID());
                stmt.setString(3, lib.getBookName());
                stmt.setString(4,lib.getBookAuthor());

                stmt.setBoolean(5, lib.getBookSite());
                stmt.setBoolean(6, lib.getBookStatus());

                stmt.setString(7, lib.getBookTag());

                stmt.setInt(8, lib.getBookStar());

                stmt.setString(9, lib.getBookPublisher());
                stmt.setString(10,lib.getBookPubDate());
                stmt.setString(11, bookSearchIndex);
                stmt.executeUpdate();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }

    }

    //删一条完整的记录
    public void Delete(Book lib)//String bookID
    {
        try
        {
            String sql = "delete from Book where BookID="+lib.getBookID();
            stmt =access.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //改 整条记录大改 bookID不能改 它是主键 通过它来唯一确定一条记录
    public Book UpDate(Book lib)
    {

        try
        {

            String sql = "update Book set BookName =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,lib.getBookName());
            stmt.executeUpdate();

            sql="update Book set BookAuthor =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,lib.getBookAuthor());
            stmt.executeUpdate();

            sql="update Book set BookSite =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setBoolean(1,lib.getBookSite());
            stmt.executeUpdate();

            sql="update Book set BookStatus =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setBoolean(1,lib.getBookStatus());
            stmt.executeUpdate();

            sql="update Book set BookTag =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,lib.getBookTag());;
            stmt.executeUpdate();

            sql="update Book set BookStar =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setInt(1,lib.getBookStar());;
            stmt.executeUpdate();

            sql="update Book set BookPublisher =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,lib.getBookPublisher());;
            stmt.executeUpdate();

            sql="update Book set BookPubDate =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,lib.getBookPubDate());;
            stmt.executeUpdate();

            String temp=null;
            temp=lib.getBookID()+","+lib.getBookName()+","+lib.getBookAuthor()+","+String.valueOf(lib.getBookSite())+","+
                    String.valueOf(lib.getBookStatus())+","+lib.getBookTag()+","+String.valueOf(lib.getBookStar())+","+
                    lib.getBookPublisher()+","+lib.getBookPubDate();

            sql="update Book set BookSearchIndex =?where BookID="+lib.getBookID();
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1,temp);;
            stmt.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lib;

    }


    //模糊查询
    public ArrayList<Book> SearchIndistinct(String searchIndex)
    {


        ArrayList<Book> arr = new ArrayList();

        try
        {
            String sql="select * from Book where BookSearchIndex like ? ";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, "%"+searchIndex+"%");
            res=stmt.executeQuery();
            while(res.next())
            {
                //第5列记录表示图书是否在馆 true表示在馆 false表示不在馆 第6条记录表示图书是否可借 true表示可借 false表示不可借
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return arr;


    }


    //通过BookID来查询
    public ArrayList<Book> SearchByBookID(String bookID)
    {
        ArrayList<Book> arr = new ArrayList();
        try
        {
            String sql="select * from Book where BookID = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, bookID);
            res=stmt.executeQuery();
            while(res.next())
            {
                //第5列记录表示图书是否在馆 true表示在馆 false表示不在馆 第6条记录表示图书是否可借 true表示可借 false表示不可借
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }
    //通过书名来查询
    public ArrayList<Book> SearchByBookName(String bookName)
    {

        ArrayList<Book> arr = new ArrayList();
        try
        {
            String sql="select * from Book where BookName = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, bookName);
            res=stmt.executeQuery();
            while(res.next())
            {
                //第5列记录表示图书是否在馆 true表示在馆 false表示不在馆 第6条记录表示图书是否可借 true表示可借 false表示不可借
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }
    //通过书的作者来查询
    public  ArrayList<Book> SearchByBookAuthor(String bookAuthor)
    {
        ArrayList<Book> arr = new ArrayList();
        try
        {
            String sql="select * from Book where BookAuthor = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, bookAuthor);
            res=stmt.executeQuery();

            while(res.next())
            {
                //第5列记录表示图书是否在馆 true表示在馆 false表示不在馆 第6条记录表示图书是否可借 true表示可借 false表示不可借
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;

    }
    //通过书的标签来查询
    public ArrayList<Book> SearchByBookTag(String bookTag)
    {
        ArrayList<Book> arr = new ArrayList();
        try
        {
            String sql="select * from Book where  BookTag = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, bookTag);
            res=stmt.executeQuery();
            while(res.next())
            {
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    //通过本书的出版商来查询
    public ArrayList<Book> SearchByBookPublisher(String bookPublisher)
    {

        ArrayList<Book> arr = new ArrayList();
        try
        {
            String sql="select * from Book where  BookPublisher = ?";
            stmt=access.connection.prepareStatement(sql);
            stmt.setString(1, bookPublisher);
            res=stmt.executeQuery();

            while(res.next())
            {
                Book Book=new Book();
                Book.setBookID(res.getString(2));
                Book.setBookName(res.getString(3));
                Book.setBookAuthor(res.getString(4));
                Book.setBookSite(res.getBoolean(5));
                Book.setBookStatus(res.getBoolean(6));
                Book.setBookTag(res.getString(7));
                Book.setBookStar(res.getInt(8));
                Book.setBookPublisher(res.getString(9));
                Book.setBookPubDate(res.getString(10));
                Book.setBookSearchIndex(" ");
                arr.add(Book);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }

}



