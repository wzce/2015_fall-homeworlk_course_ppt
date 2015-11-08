import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.mongodb.MongoClient;


public class Homework2 {
	public void mysqlCreateAndInsert(){
		mySqlCreateTable();
		sqlInsertTeacherData();
		sqlInsertStudentInfoData();
		sqlInsertCourseData();
		sqlInsertScoreData();
	}
	
	
	private void mySqlCreateTable(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("DROP TABLE IF EXISTS `teacher`");
		list.add("DROP TABLE IF EXISTS `student`");
		list.add("DROP TABLE IF EXISTS `score`");
		list.add("DROP TABLE IF EXISTS `course`");
		
		String create_teacher="create table `teacher`(`teacher_id` int(10) not null,"
				+ "`teacher_name` varchar(255)not null,`teacher_department` varchar(255) not null,primary key(`teacher_id`))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_teacher);
		
		String create_student="create table `student`(`student_id` int(10) not null,"
				+ "`student_name` varchar(255) not null,`area` varchar(255) not null,primary key(student_id))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_student);
		
		String create_score="create table score(`score_id` int(10) not null,`student_id` int(10) not null,"
				+ "`course_id` int(10) not null,"
				+ "`grade` int(4) default null,"
				+ "primary key(score_id))ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_score);/*待定*/
		
		String create_course= "create table course(`course_id` int(10) not null,"
				+ "`course_name` varchar(255) not null,`teacher_id` int(10) not null,`term` varchar(255) not null,primary key(course_id))"
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8";
		list.add(create_course);
		
		try {
			Connection conn=getMysqlConnection();
			Statement stmt = conn.createStatement();
			
			for(int i=0;i<list.size();i++){
				stmt.executeUpdate(list.get(i));
			}
			stmt.close();
		     conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("sql数据库建表出错！原因："+e.toString());
		}
		
		//System.out.println("建表成功！");
	}
	
	private void sqlInsertCourseData(){
		/******************/
		String path="ex2/course.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	String sql1;
        	while((line=(String)br.readLine())!=null){
        		if(count==0){
        			count=1;
        			continue;
        		}
//        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		sql1="INSERT INTO `course` (`course_id`, `course_name`, `teacher_id`, `term`) VALUES ('"+str[0]+"', '"+str[1]+"', '"+str[2]+"', '"+str[3]+"')";
        		stmt.executeUpdate(sql1);
        	}//end while
       
        	stmt.close();
        	conn.close();
        	//System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入课程数据，出错原因："+e.toString()+"line="+line);
        }

	}
	
	
	private void sqlInsertStudentInfoData(){
		String path="ex2/student.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	String sql1;
        	while((line=(String)br.readLine())!=null){
        		if(count==0){
        			count=1;
        			continue;
        		}
//        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		sql1="INSERT INTO `student` (`student_id`, `student_name`, `area`) VALUES ('"+str[0]+"', '"+str[1]+"', '"+str[2]+"')";
        		stmt.executeUpdate(sql1);
        	}//end while
       
        	stmt.close();
        	conn.close();
        	//System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入学生数据，出错原因："+e.toString()+"line="+line);
        }
		
	}
	
	private void sqlInsertTeacherData(){
		String path="ex2/teacher.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	String sql1;
        	while((line=(String)br.readLine())!=null){
        		if(count==0){
        			count=1;
        			continue;
        		}
//        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		sql1="INSERT INTO `teacher` (`teacher_id`, `teacher_name`, `teacher_department`) VALUES ('"+str[0]+"', '"+str[1]+"', '"+str[2]+"')";
        		stmt.executeUpdate(sql1);
        	}//end while
       
        	stmt.close();
        	conn.close();
        	//System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入教师数据，出错原因："+e.toString()+"line="+line);
        }
	}
	
	
	private void sqlInsertScoreData(){
		String path="ex2/score.txt"; 				//读取文件的路径
        String encoding="utf-8";		//以utf-8的编码方式读取文件
        Connection conn;
		 String line=null;
        try{ 
        	InputStreamReader read = new InputStreamReader(
                    new FileInputStream(new File(path)),encoding);
        	BufferedReader br = new BufferedReader(read);
        	conn = getMysqlConnection();
			Statement stmt = conn.createStatement();
        	int count=0;	//用于标记第一行不读入
        	String sql1;
        	while((line=(String)br.readLine())!=null){
        		if(count==0){
        			count++;
        			continue;
        		}
//        		line=line.replace("'","''");
        		String str[]=line.split("	");
        		sql1="INSERT INTO `score` (`score_id`, `student_id`,`course_id`, `grade`) VALUES ('"+count+"','"+str[0]+"', '"+str[1]+"', '"+str[2]+"')";
        		stmt.executeUpdate(sql1);
        		count++;
        	}//end while
       
        	stmt.close();
        	conn.close();
        	//System.out.println("-----------------end--------------------");
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println();
        	System.out.println("写入课程分数数据，出错原因："+e.toString()+"line="+line);
        }
	}
	
	
	public void mysqlQuery(){
		
		String sql="select course_name,grade from score,course,teacher "
				+ "where score.student_id='131250148' "
				+ "and score.course_id=course.course_id "
				+ "and course.teacher_id=teacher.teacher_id "
				+ "and teacher.teacher_name='刘嘉'";
		
		try {
			Connection conn=getMysqlConnection();
			Statement stmt = conn.createStatement();
			ResultSet  rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				System.out.println("课程："+rs.getString("course_name")+" ; 得分："+rs.getInt("grade"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void mongoCreateAndInsert(){
		
	}
	
	public void mongoQuery(){
		try {
			MongoClient client=getMongoClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * get mysql connection
	 * @return
	 * @throws Exception
	 */
	public Connection getMysqlConnection() throws Exception{
		//get items from resources/config.properties
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
		properties.load(new FileInputStream(new File("src/main/resources/config.properties")));
		
		String mysqlIP = properties.getProperty("mysql.server.ip");
		String mysqlPort =  properties.getProperty("mysql.server.port");
		String dbName =  properties.getProperty("mysql.db.ex2");
		String loginName =  properties.getProperty("mysql.login.name");
		String password =  properties.getProperty("mysql.login.password");
		String url = "jdbc:mysql://"+mysqlIP+":"+mysqlPort+"/"+dbName+"?"
                + "user="+loginName+"&password="+password+"&useUnicode=true&characterEncoding=UTF8";
		
		//load class driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//return connection
		Connection connection = DriverManager.getConnection(url);
		return connection;
	}
	
	
	public MongoClient getMongoClient() throws Exception{
		//get items from resources/config.properties
		Properties properties = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
		properties.load(new FileInputStream(new File(path)));
		
		String mongoIP = properties.getProperty("mongo.server.ip");
		int mongoPort = Integer.parseInt(properties.getProperty("mongo.server.port"));
		String dbName = properties.getProperty("mongo.db.ex2");
		
		//return mongoClient
		MongoClient mongoClient = new MongoClient(mongoIP, mongoPort);
		return mongoClient;
	}
	
	
	public static void main(String args[]){
		long x=System.currentTimeMillis();
		System.out.println("开始计时："+x);
		
		Homework2 test2=new Homework2();
		test2.mysqlCreateAndInsert();
		//test2.mongoQuery();
		
		long y=System.currentTimeMillis();
		System.out.println("开始计时："+y+";   "+"累计耗时:"+((double)(y-x)+"ms"));
	}
	
}
