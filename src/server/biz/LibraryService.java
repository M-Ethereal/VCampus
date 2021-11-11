package server.biz;

import server.dao.BookDao;
import server.dao.LendRecordDao;
import vo.Book;
import vo.LendRecord;

import java.util.ArrayList;

public class LibraryService
{
    public static ArrayList<Book>RecommendByStudentID(String stuID)
    {
        ArrayList<Book> arr=new ArrayList<Book>();
        ArrayList<LendRecord> lend=new ArrayList<LendRecord>();
        String str=stuID.substring(0, 2);
        String bookID=null;
        LendRecordDao lendRecordDao=new LendRecordDao();
        BookDao BookDao=new BookDao();
        lend=lendRecordDao.returnAll();
        for(int i=0;i<lend.size();i++)
        {
            if(lend.get(i).getRecordStudentID().substring(0, 2).equals(str)==true)//判断学号前两位是否相等
            {
                bookID=lend.get(i).getRecordBookID();//得到书号
                System.out.println("i="+i+" 书号="+bookID+" 回传大小="+BookDao.SearchByBookID(bookID).size());
                Book book=BookDao.SearchByBookID(bookID).get(0);
                arr.add(book);//添加到预设书的数组中
            }
        }

        //得到一个lib 书的数 组 借阅的人全是同一个学院的

        int K[]=new int[arr.size()];//统计数组 统计每本书在该数组中出现了几次
        int k=0;
        for(int i=0;i<arr.size();i++)
        {
            k=0;
            for(int j=0;j<arr.size();j++)
            {
                if(arr.get(i).getBookID().equals(arr.get(j).getBookID())==true)//如果有两个书号一样的
                {
                    k++;
                }
            }
            K[i]=k;//i是第几个书
        }
        class Book2 extends Book
        {
            private Book Book;
            private int lendTimes;
            public void setLendTimes(int lendTimes)
            {
                this.lendTimes=lendTimes;
            }
            public void setBook(Book Book)
            {
                this.Book=Book;
            }
            public Book getBook()
            {
                return Book;
            }
            public int getLendTimes()
            {
                return lendTimes;
            }
        }
        ArrayList<Book2> arr2=new ArrayList<Book2>();
        for(int i=0;i<arr.size();i++)//将书和他的借阅次数打包成一个类
        {
            Book2 lib2=new Book2();
            lib2.setBook(arr.get(i));
            lib2.setLendTimes(K[i]);
            arr2.add(lib2);
        }
        for(int i=0;i<arr2.size();i++)//去重  	去掉重复的书
        {
            for(int j=0;j<arr2.size();j++)
            {
                if(arr2.get(i).getBook().getBookID().equals(arr2.get(j).getBook().getBookID())==true&&i!=j)
                {
                    arr2.remove(j);
                }
            }
        }
        for(int i=0;i<arr2.size();i++)
        {
            Book2 temp=new Book2();
            for(int j=0;j<arr2.size();j++)
            {
                if(arr2.get(i).getLendTimes()>arr2.get(j).getLendTimes())
                {
                    temp.setLendTimes(arr2.get(i).getLendTimes());
                    temp.setBook(arr2.get(i).getBook());
                    arr2.get(i).setLendTimes(arr2.get(j).getLendTimes());
                    arr2.get(i).setBook(arr2.get(j).getBook());
                    arr2.get(j).setLendTimes(temp.getLendTimes());
                    arr2.get(j).setBook(temp.getBook());
                }
            }
        }
        for(int i=0;i<arr2.size();i++)
        {
            System.out.println("    "+arr2.get(i).getBook().getBookName()+"     "+arr2.get(i).getLendTimes());
        }
        ArrayList<Book> arr3=new ArrayList<Book>();
        for(int i=0;i<arr2.size();i++)
        {
            arr3.add(arr2.get(i).getBook());
        }
        return arr3;
    }


    public static boolean LendBook(LendRecord lend)//模拟从图书馆借书的操作
    {
        BookDao BookDao=new BookDao();//操作书籍的数据库 实现某书的记录的改变
        Book Book=new Book();
        Book=BookDao.SearchByBookID(lend.getRecordBookID()).get(0);
        Book.setBookSite(false);
        Book.setBookStatus(false);
        BookDao.UpDate(Book);
        System.out.println("调试中site="+Book.getBookSite());
        LendRecordDao lendRecordDao=new LendRecordDao();//操作借阅记录的数据库 新增借阅记录
        lendRecordDao.Insert(lend);
        return true;
    }
    public static boolean ReturnBook(LendRecord lend)//模拟从图示馆还书操作
    {
        LendRecordDao lendRecordDao=new LendRecordDao();
        BookDao BookDao=new BookDao();//操作书籍的数据库 实现某书的记录的改变
        Book lib=new Book();
        lib=BookDao.SearchByBookID(lend.getRecordBookID()).get(0);
        lib.setBookSite(true);
        lib.setBookStatus(true);
        BookDao.UpDate(lib);
        lendRecordDao.UpDate(lend);
        return true;
    }


}
