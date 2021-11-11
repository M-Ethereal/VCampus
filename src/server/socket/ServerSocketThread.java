package server.socket;

//import server.biz.UserManagementImp;
import server.biz.*;
import server.dao.*;
import server.exception.*;
import util.MessageType;
import vo.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerSocketThread implements Runnable{
    private Socket clientSocket;
    private LoginSignUpService loginSignUpService = new LoginSignUpService();
    private StudentDao sd = new StudentDao();
    private RetailerDao rd = new RetailerDao();
    private TeacherDao td = new TeacherDao();
    private DoctorDao dd = new DoctorDao();
    private CourseDao courseDao = new CourseDao();
    private GradeDao gradeDao = new GradeDao();
    private CourseService courseService = new CourseService();
    private BankDao bd = new BankDao();
    private BankService bs = new BankService();
    private MedicalAdviceDao medicalAdviceDao = new MedicalAdviceDao();
    private RegistrationRecordDao registrationRecordDao = new RegistrationRecordDao();
    private DoctorScheduleDao doctorScheduleDao = new DoctorScheduleDao();
    private HospitalService hospitalService = new HospitalService();
    private BookDao libDao = new BookDao();
    private LendRecordDao lendDao = new LendRecordDao();
    private LibraryService libOp = new LibraryService();
    private GoodsDao goodsDao = new GoodsDao();
    private ShopRecordDao srDao = new ShopRecordDao();
    private ShopService shopservice = new ShopService();
    private PlaceDao placeDao = new PlaceDao();
    private PlaceAppointmentRecordDao placeAppointmentRecordDao = new PlaceAppointmentRecordDao();

    ServerSocketThread(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public synchronized void run() {

        try{
            ObjectInputStream message = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            Message object = (Message)message.readObject();
            System.out.println("已与客户端建立连接，当前客户端ip为：" + clientSocket.getInetAddress().getHostAddress());
            System.out.println(object.getMessageType());
            Message serverResponse = new Message();
            //System.out.println("1");
            switch (object.getMessageType())
            {
                //登录
                case MessageType.userLogin:
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        Login lg = (Login)object.getData();
                        System.out.println("Socket: 用户ID" + lg.getId() + "用户密码" + lg.getPwd() + "用户类型" + lg.getUserType());
                        Object user = loginSignUpService.checkPwd(lg.getId(), lg.getPwd(), object.getUserType());
                        serverResponse.setData(user);
                        serverResponse.setLastOperState(true);


                    } catch (UserUnsignedException e){
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("用户未注册！");
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (WrongPasswordException e){
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("密码错误！");
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.register:
                    try
                    {
                        SignUp su = (SignUp)object.getData();
                        //System.out.println("2:"+lg.getId()+lg.getPwd());
                        if(loginSignUpService.checkSignUp(su.getId(), su.getPwd(), su.getUserType())==2)
                        {
                            serverResponse.setLastOperState(true);
                            serverResponse.setMessageType(MessageType.operFeedback);
                        }
                        else if(loginSignUpService.checkSignUp(su.getId(), su.getPwd(), su.getUserType())==1)
                        {
                            serverResponse.setLastOperState(false);
                            serverResponse.setErrorMessage("用户已存在！");
                            serverResponse.setMessageType(MessageType.operFeedback);
                        }
                        else {
                            serverResponse.setLastOperState(false);
                            serverResponse.setErrorMessage("非法注册，请联系管理员！");
                            serverResponse.setMessageType(MessageType.operFeedback);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;


                    //医院-医生
                case MessageType.doc_query_all:
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Doctor> docs = dd.queryAll();

                        if(docs!=null) serverResponse.setData(docs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.doc_query_doc://按照id查找医生
                    try {
                        String docId = object.getExtraMessage();
                        Doctor doctor = new Doctor();
                        doctor = dd.query(docId);
                        serverResponse.setData(doctor);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_advice_query_stu://按照学生id查找咨询记录
                    try {
                        String stuId = object.getExtraMessage();
                        ArrayList<MedicalAdvice> medicalAdvices = new ArrayList<MedicalAdvice>();
                        medicalAdvices = medicalAdviceDao.queryByStuId(stuId);
                        System.out.println("socket:"+medicalAdvices.size());
                        serverResponse.setData(medicalAdvices);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_advice_query_doc://按照医生id查找咨询记录
                    try {
                        String docId = object.getExtraMessage();
                        ArrayList<MedicalAdvice> medicalAdvices = new ArrayList<MedicalAdvice>();
                        medicalAdvices = medicalAdviceDao.queryByDocId(docId);
//                        System.out.println("socket:"+medicalAdvices.size());
                        serverResponse.setData(medicalAdvices);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_advice_query_relevant:
                    try {
                        MedicalAdvice medicalAdvice = (MedicalAdvice) object.getData();
                        String stuId = medicalAdvice.getStuId();
                        String docId = medicalAdvice.getDocId();
                        ArrayList<MedicalAdvice> medicalAdvices = medicalAdviceDao.queryRelevant(docId,stuId);
                        serverResponse.setData(medicalAdvices);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_advice_insert://添加咨询记录
                    try {
                        MedicalAdvice medicalAdvice = (MedicalAdvice) object.getData();
                        medicalAdviceDao.insert(medicalAdvice);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

                case MessageType.stu_res_query://按照学生id查找挂号记录
                    try{
                        String stuId = object.getExtraMessage();
                        ArrayList<RegistrationRecord> registrationRecords = new ArrayList<RegistrationRecord>();
                        registrationRecords = registrationRecordDao.queryByStuId(stuId);
                        serverResponse.setData(registrationRecords);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_res_insert://添加挂号记录
                    try{
                        RegistrationRecord registrationRecord = new RegistrationRecord();
                        registrationRecord = (RegistrationRecord) object.getData();
                        registrationRecordDao.insert(registrationRecord);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.doc_schedule_query://查找医生的出诊安排
                    try {
                        String docId = object.getExtraMessage();
                        ArrayList<DoctorSchedule> doctorSchedules = doctorScheduleDao.queryById(docId);
                        serverResponse.setData(doctorSchedules);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

                case MessageType.doc_rank://给医生评分
                    try {
                        MedicalAdvice medicalAdvice = (MedicalAdvice) object.getData();
                        String rank = object.getExtraMessage();
                        hospitalService.docRank(medicalAdvice,rank);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

                case MessageType.doc_advice_ans:
                    try {
                        MedicalAdvice medicalAdvice = (MedicalAdvice) object.getData();
                        medicalAdviceDao.update(medicalAdvice);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

                case MessageType.doc_schedule_add://添加医生出诊信息
                    try {
                        DoctorSchedule doctorSchedule = (DoctorSchedule) object.getData();
                        doctorScheduleDao.insert(doctorSchedule);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (RecordAlreadyExistException e) {
                        serverResponse.setErrorMessage("出错：请勿重复添加");
                        serverResponse.setLastOperState(false);
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

                case MessageType.doc_schedule_del://添加医生出诊信息
                    try {
                        DoctorSchedule doctorSchedule = (DoctorSchedule) object.getData();
                        doctorScheduleDao.delete(doctorSchedule);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }

                    break;

//***************************************************************** 课程模块 **************************************************************************

                    //课程-学生
                case MessageType.course_init:

                case MessageType.course_query:
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        Student student = new Student();
                        student = (Student) object.getData();
                        ArrayList<Course> courses_all = courseDao.queryThisTermCourseList(student.getNumber(), object.getExtraMessage());

                        if(courses_all!=null) {
                            //System.out.println(courses_all);
                            serverResponse.setData(courses_all);
                        }
                        else System.out.println("null");

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//查询当前学期的所有课程
                case MessageType.course_query_admin:
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Course> courses_all = courseDao.queryThisTermCourseList_true(object.getExtraMessage());

                        if(courses_all!=null) {
                            serverResponse.setData(courses_all);
                        }
                        else System.out.println("null");

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.course_query_course:
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        Course courses_all = courseDao.queryByCID(object.getExtraMessage());

                        if(courses_all!=null) {
                            serverResponse.setData(courses_all);
                        }
                        else System.out.println("null");

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
//选课
                case MessageType.course_choose://已满的情况在前端判断，冲突的情况在后端判断
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        Grade grade = (Grade) object.getData();
                        //gradeDao.insert(grade);
                        courseService.courseChoose(grade);
                        serverResponse.setLastOperState(true);

                    } catch (RecordAlreadyExistException e) {
                        e.printStackTrace();
                        serverResponse.setLastOperState(false);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setErrorMessage("不能重复选择课程");

                    } catch(ConflictException e)
                    {
                        serverResponse.setLastOperState(false);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setErrorMessage("课程时间冲突");

                    } catch (OutOfLimitException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setErrorMessage("课程时间冲突");

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.course_my_table:
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        Student student = (Student) object.getData();
                        String seme = object.getExtraMessage();
                        ArrayList<Course> myTable = courseService.myTimeTable(student,seme);

                        if(myTable!=null) {
                            serverResponse.setData(myTable);
                        }
                        else System.out.println("null");

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } catch (RecordNotFoundException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setErrorMessage("该学期您还没有课程");

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//退课
                case MessageType.course_giveup:
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        Grade grade = (Grade) object.getData();
                        System.out.println("socket:"+grade.getStuName()+grade.getCourseName());
                        //gradeDao.delete(grade);
                        courseService.giveupChoose(grade);
                        serverResponse.setLastOperState(true);

                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.course_query_grade://查询成绩表
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        String stuID = object.getExtraMessage();
                        //ArrayList<Grade> grades = gradeDao.queryById(stuID);
                        ArrayList<GradeTable> gradeTables = courseService.queryGradeTable(stuID);
                        serverResponse.setLastOperState(true);
                        if(gradeTables!=null) serverResponse.setData(gradeTables);

                    } catch (RecordNotFoundException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("数据库中似乎发生了一些错误");
                        e.printStackTrace();

                    } catch (SQLException e) {
                        serverResponse.setLastOperState(false);
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//管理员加课
                case MessageType.course_add:
                    try {
                        Course course = (Course) object.getData();
                        courseDao.insert(course);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (ConflictException e) {
                        System.out.println("老师冲突");
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("同一位老师不能同时上两门课");
                        e.printStackTrace();

                    } catch (ClassroomConflictException e) {
                        System.out.println("教室冲突");
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("与已有的课程发生教室冲突");
                        e.printStackTrace();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case  MessageType.course_exam_add:
                    try
                    {
                        Course course = new Course();
                        course = (Course) object.getData();
                        course.setExam(true);
                        courseDao.update(course);
                        System.out.println(course.getCourseName() + " " + course.getExamTime() + " " + course.getExamPlace());
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//查询老师课表
                case MessageType.course_teacher_table:
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        Teacher teacher = (Teacher) object.getData();
                        String teacherName = teacher.getName();
                        String seme = object.getExtraMessage();
                        ArrayList<Course> myTable = courseDao.queryThisTermCourseList_Teacher(teacherName,seme);

                        if(myTable!=null) {
                            serverResponse.setData(myTable);
                        }
                        else System.out.println("null");

                    }  catch (RecordNotFoundException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("您本学期没有课程");
                        e.printStackTrace();

                    } catch (SQLException e) {
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.course_studentList_query://查询选则该课程的学生
                    try{
                        String courseID = object.getExtraMessage();
                        //ArrayList<Grade> grades = gradeDao.queryById(stuID);
                        //ArrayList<GradeTable> gradeTables = courseService.queryGradeTable(stuID);
                        ArrayList<Grade> grades = gradeDao.queryByCourseID(courseID);
                        System.out.println(courseID + " " + grades.get(0).getCourseName());
                        if(grades!=null) serverResponse.setData(grades);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                    } catch (RecordNotFoundException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("数据库中似乎发生了一些错误");
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case  MessageType.course_grade_add:
                    try
                    {
                        Grade grade = new Grade();
                        grade = (Grade) object.getData();
                        courseService.AddGrade(grade);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//*******************************************图书馆开始*************************************************************
                case MessageType.outputallbooks://返回所有书籍
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchIndistinct("");
// 	    			    System.out.println("数组的大小=="+arr.size());
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputallbooksbystudy://学业推荐书籍
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libOp.RecommendByStudentID((String)object.getData());
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.outputallbooksbyhappy:	//休闲书籍推荐
                    try
                    {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchIndistinct("");//返回所有的书籍 前端再分
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.outputbyindistinctsearch:	//模糊查询
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchIndistinct((String)object.getData());
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbybookidsearch:	//根据书的ID查询
                    try
                    {
                        String stuId = object.getUserId();
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchByBookID(stuId);
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbybooknamesearch:	//根据书名查询
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchByBookName((String)object.getData());
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbybookauthorsearch:	//根据作者查询
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Book> arr = new ArrayList<Book>();
                        arr=libDao.SearchByBookAuthor((String)object.getData());
                        serverResponse.setData(arr);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbysearchbyrecordstudentidisreturn:	//根据学号查询我的已还书
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        ArrayList<LendRecord> arr = new ArrayList<LendRecord>();
                        arr=lendDao.SearchByRecordStudentIDIsReturn((String)object.getData());
                        serverResponse.setData(arr);
                        serverResponse.setLastOperState(true);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbysearchbyrecordstudentidnotreturn:	//根据学号查询我的未还书
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);

                        ArrayList<LendRecord> arr = new ArrayList<LendRecord>();
                        arr=lendDao.SearchByRecordStudentIDNotReturn((String)object.getData());
// 	    			    System.out.println("数组的大小=="+arr.size());
                        serverResponse.setData(arr);
                        serverResponse.setLastOperState(true);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.insertbook:	//插入书籍
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        libDao.Insert((Book)object.getData());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.deletelibbook:	//删除书籍
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                        libDao.Delete((Book)object.getData());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.returnbook:	//还书
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        LendRecord lend=(LendRecord)object.getData();
                        Book lib=libDao.SearchByBookID(lend.getRecordBookID()).get(0);
                        libDao.UpDate(lib);
                        lendDao.UpDate((LendRecord)object.getData());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.lendbook:	//借书
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        LendRecord lend=(LendRecord)object.getData();
                        Book lib=libDao.SearchByBookID(lend.getRecordBookID()).get(0);
                        libDao.UpDate(lib);
                        libOp.LendBook((LendRecord)object.getData());


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.updatebook:	//更新书籍信息
                    try
                    {

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        libDao.UpDate((Book)object.getData());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case MessageType.outputbysearchbyrecordid:	//通过查询借阅ID来查询记录
                    try
                    {
                        String rId = object.getExtraMessage();
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<LendRecord> arr=new ArrayList<LendRecord>();
                        arr = lendDao.SearchByRecordID(rId);
                        serverResponse.setData(arr);
                        serverResponse.setLastOperState(true);


                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                    
//*****************************************************图书馆结束****************************************************************




//*****************************************************商店开始****************************************************************

                case MessageType.shopQuery:
                {
                    try{
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Goods> arr=new ArrayList<Goods>();
                        arr=goodsDao.NameQuery(object.getExtraMessage());
                        serverResponse.setData(arr);

                    }catch(Exception e){

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                }

                case MessageType.shopInit:
                {
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<Goods> garr=new ArrayList<Goods>();
                        garr=goodsDao.AllQuery();
                        serverResponse.setData(garr);
                    }catch(Exception e){

                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                }
                case MessageType.PurchaseRecordQuery:
                    try {
                        String stuId = object.getExtraMessage();
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<ShopRecord> srarr=new ArrayList<ShopRecord>();
                        srarr=srDao.bRecordQuery(stuId);
                        serverResponse.setData(srarr);

                    }catch(Exception e){
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.SellingRecordQuery:
                    try {
                        String sId = object.getExtraMessage();
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ArrayList<ShopRecord> srarr=new ArrayList<ShopRecord>();
                        srarr=srDao.sRecordQuery(sId);
                        serverResponse.setData(srarr);

                    }catch(Exception e){
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.add_product:
                {
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        goodsDao.InsertGoods((Goods) object.getData());
                    }catch(Exception e){
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                }
                case MessageType.Buy:
                {
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);

                        shopservice.Purchase((Goods)object.getData(),Integer.valueOf(object.getExtraMessage()), object.getUserId());

                    } catch (OutOfLimitException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("商品库存不足");
                        e.printStackTrace();

                    } catch (InsufficientBalanceException e) {
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("账户余额不足");
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                }
                case MessageType.Score:
                {
                    try {
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(true);
                        ShopRecord shopRecord = (ShopRecord) object.getData();
                        shopservice.Score(shopRecord, serverResponse.getExtraMessage());
                    }catch(Exception e){
                        serverResponse.setMessageType(MessageType.operFeedback);
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("wrongMessage");
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                }

                case MessageType.shop_queryStudent:
                    try
                    {
                        String stuId = object.getExtraMessage();
                        System.out.println("socket: " + stuId);
                        Student ss=new Student();
                        ss=sd.query(stuId);
                        serverResponse.setData(ss);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.shop_queryRetailer:
                    try
                    {
                        String reId = object.getExtraMessage();
                        System.out.println("socket: " + reId);
                        Retailer retailer = new Retailer();
                        retailer = rd.query(reId);
                        serverResponse.setData(retailer);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

//*****************************************************商店结束****************************************************************













//***********************************************银行-学生 开始***********************************************************************
                case MessageType.phone_Pay:
                    try
                    {
                        BankRecord temp = new BankRecord();
                        temp = (BankRecord) object.getData();
                        System.out.println("socket0:" + temp.getuID());
                        bs.bankPayWIFIA(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        System.out.println("socket 1");

                    } catch (OutOfLimitException e) {
                        System.out.println("socket 2");
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("一卡通余额不足！");
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.wifi_Pay:
                    try
                    {
                        BankRecord temp = new BankRecord();
                        temp = (BankRecord) object.getData();
                        System.out.println("socket0:" + temp.getuID());
                        bs.bankPayWIFIB(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        System.out.println("socket 1");

                    } catch (OutOfLimitException e) {
                        System.out.println("socket 2");
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("一卡通余额不足！");
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.tuition_Pay:
                    try
                    {
                        BankRecord temp = new BankRecord();
                        temp = (BankRecord) object.getData();
                        System.out.println("socket0:" + temp.getuID());
                        bs.bankPayTuition(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        System.out.println("socket 1");

                    } catch (OutOfLimitException e) {
                        System.out.println("socket 2");
                        serverResponse.setLastOperState(false);
                        serverResponse.setErrorMessage("一卡通余额不足！");
                        e.printStackTrace();

                    } finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.card_Invest:
                    try
                    {
                        String number= object.getExtraMessage();
                        BankRecord temp = new BankRecord();
                        temp = (BankRecord) object.getData();
                        System.out.println("socket0:" + temp.getuID());
                        bs.bankInvestCard(temp, Integer.valueOf(number));
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);
                        System.out.println("socket 1");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;


                case MessageType.record_Query:
                    try
                    {
                        String temp = object.getUserId();
                        ArrayList<BankRecord> rec = new ArrayList<BankRecord>();
                        rec = bd.query(temp);
                        serverResponse.setData(rec);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.account_Query:
                    try
                    {
                        String temp = object.getUserId();
                        String record = object.getExtraMessage();
                        ArrayList<BankRecord> rec = new ArrayList<BankRecord>();
                        rec = bd.queryByRecord(temp,record);
                        serverResponse.setData(rec);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;


//***********************************************银行-学生 结束***********************************************************************



//***********************************************场馆 开始***********************************************************************


                case MessageType.place_query_all:
                    try
                    {
                        String placePos = object.getExtraMessage();
                        ArrayList<Place> placeArrayList = new ArrayList<Place>();
                        placeArrayList = placeDao.queryByPos(placePos);
                        serverResponse.setData(placeArrayList);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.stu_ap_query://按学生id查询预约记录
                    try
                    {
                        String stuId = object.getExtraMessage();
                        ArrayList<PlaceAppointmentRecord> placeAPArrayList = new ArrayList<PlaceAppointmentRecord>();
                        placeAPArrayList = placeAppointmentRecordDao.queryByStuId(stuId);
                        serverResponse.setData(placeAPArrayList);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.place_ap_query://按场馆名查询预约记录
                    try
                    {
                        String placeName = object.getExtraMessage();
                        ArrayList<PlaceAppointmentRecord> placeAPArrayList = new ArrayList<PlaceAppointmentRecord>();
                        placeAPArrayList = placeAppointmentRecordDao.queryByPlaceName(placeName);
                        serverResponse.setData(placeAPArrayList);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.place_ap_insert://预约
                    try
                    {
                        PlaceAppointmentRecord placeAppointmentRecord = (PlaceAppointmentRecord) object.getData();
                        placeAppointmentRecordDao.insert(placeAppointmentRecord);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.place_ap_delete://取消预约
                    try
                    {
                        PlaceAppointmentRecord placeAppointmentRecord = (PlaceAppointmentRecord) object.getData();
                        placeAppointmentRecordDao.delete(placeAppointmentRecord);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;

                case MessageType.place_query_by_name:
                    try
                    {
                        String placeName = object.getExtraMessage();
                        Place place = new Place();
                        place = placeDao.queryByName(placeName);
                        serverResponse.setData(place);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;


//***********************************************场馆 结束***********************************************************************











                    //管理员部分
                case  MessageType.outputAllStudent:
                    try
                    {
                        ArrayList<Student> temp=new ArrayList<Student>();
                        temp = sd.queryAll();
                        System.out.print(temp.size());
                        serverResponse.setData(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.outputAllTeacher:
                    try
                    {
                        ArrayList<Teacher> temp=new ArrayList<Teacher>();
                        temp= td.queryAll();
                        System.out.print(temp.size());
                        serverResponse.setData(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.outputAllRetailer:
                    try
                    {
                        ArrayList<Retailer> temp=new ArrayList<Retailer>();
                        temp= rd.queryAll();
                        System.out.print(temp.size());
                        serverResponse.setData(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.outputAllDoctor:
                    try
                    {
                        ArrayList<Doctor> temp=new ArrayList<Doctor>();
                        temp= dd.queryAll();
                        System.out.print(temp.size());
                        serverResponse.setData(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.addStudent:
                    try
                    {
                        Student temp=new Student();
                        temp=(Student) object.getData();
                        sd.insert(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.deleteStudent:
                    try
                    {
                        String temp= object.getUserId();
                        sd.delete(temp);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.queryStudent:
                    try
                    {
                        String temp= object.getUserId();
                        Student ss=new Student();
                        ss=sd.query(temp);
                        serverResponse.setData(ss);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.updateStudent:
                    try
                    {
                        Student ss=new Student();
                        ss=(Student) object.getData();
                        sd.update(ss);
                        System.out.println(ss.getName() + ss.getId());
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.addTeacher:
                    try
                    {
                        Teacher tt=new Teacher();
                        tt=(Teacher) object.getData();
                        td.insert(tt);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.deleteTeacher:
                    try
                    {
                        String temp= object.getUserId();
                        td.delete(temp);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.queryTeacher:
                    try
                    {
                        String temp= object.getUserId();
                        Teacher tt=new Teacher();
                        tt=td.query(temp);
                        serverResponse.setData(tt);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.updateTeacher:
                    try
                    {
                        Teacher tt=new Teacher();
                        tt=(Teacher) object.getData();
                        td.update(tt);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.addRetailer:
                    try
                    {
                        Retailer rr=new Retailer();
                        rr=(Retailer) object.getData();
                        rd.insert(rr);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.deleteRetailer:
                    try
                    {
                        String temp= object.getUserId();
                        rd.delete(temp);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.queryRetailer:
                    try
                    {
                        String temp= object.getUserId();
                        Retailer rr=new Retailer();
                        rr=rd.query(temp);
                        serverResponse.setData(rr);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.updateRetailer:
                    try
                    {
                        Retailer rr=new Retailer();
                        rr=(Retailer) object.getData();
                        rd.update(rr);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.addDoctor:
                    try
                    {
                        Doctor doc=new Doctor();
                        doc=(Doctor) object.getData();
                        dd.insert(doc);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.deleteDoctor:
                    try
                    {
                        String temp= object.getUserId();
                        dd.delete(temp);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.queryDoctor:
                    try
                    {
                        String temp= object.getUserId();
                        Doctor doc=new Doctor();
                        doc=dd.query(temp);
                        serverResponse.setData(doc);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.updateDoctor:
                    try
                    {
                        Doctor doc=new Doctor();
                        doc=(Doctor) object.getData();
                        dd.update(doc);;
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
                case  MessageType.queryStudentByMajor:
                    try
                    {
                        ArrayList<Student> temp=new ArrayList<Student>();
                        Student ss=new Student();
                        ss=(Student) object.getData();
                        temp = sd.queryByMajor(ss);
                        serverResponse.setData(temp);
                        serverResponse.setLastOperState(true);
                        serverResponse.setMessageType(MessageType.operFeedback);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finally {
                        ObjectOutputStream response = new ObjectOutputStream(clientSocket.getOutputStream());
                        response.writeObject(serverResponse);
                    }
                    break;
            }
        }
//        catch(){
//
//        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
