package dbConnection
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql
import java.sql.*
import com.microsoft.sqlserver.jdbc.SQLServerDriver
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import java.sql.Connection
import internal.GlobalVariable

public class connectDB {
	private static Connection connection = null;

	//keyword connectLOS
	@Keyword
	public connectLOS(){
		String servername = findTestData('Login/Login').getValue(1, 9)

		String instancename = findTestData('Login/Login').getValue(2, 9)

		String username = findTestData('Login/Login').getValue(3, 9)

		String password = findTestData('Login/Login').getValue(4, 9)

		String database = findTestData('Login/Login').getValue(5, 9)

		String driverclassname = findTestData('Login/Login').getValue(6, 9)

		String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

		return Sql.newInstance(url,username,password,driverclassname)
	}

	//keyword connectFOU
	@Keyword
	public connectFOU(){
		String servername = findTestData('Login/Login').getValue(1, 7)

		String instancename = findTestData('Login/Login').getValue(2, 7)

		String username = findTestData('Login/Login').getValue(3, 7)

		String password = findTestData('Login/Login').getValue(4, 7)

		String database = findTestData('Login/Login').getValue(5, 7)

		String driverclassname = findTestData('Login/Login').getValue(6, 7)

		String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

		return Sql.newInstance(url,username,password,driverclassname)
	}

	//keyword connectCAMUNDASIT
	@Keyword
	public connectCAMUNDASIT(){
		String servername = findTestData('Login/Login').getValue(1, 8)

		String instancename = findTestData('Login/Login').getValue(2, 8)

		String username = findTestData('Login/Login').getValue(3, 8)

		String password = findTestData('Login/Login').getValue(4, 8)

		String database = findTestData('Login/Login').getValue(5, 8)

		String driverclassname = findTestData('Login/Login').getValue(6, 8)

		String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

		return Sql.newInstance(url,username,password,driverclassname)
	}

	//keyword connectTAX
	@Keyword
	public connectTAX(){
		String servername = findTestData('Login/Login').getValue(1, 10)

		String instancename = findTestData('Login/Login').getValue(2, 10)

		String username = findTestData('Login/Login').getValue(3, 10)

		String password = findTestData('Login/Login').getValue(4, 10)

		String database = findTestData('Login/Login').getValue(5, 10)

		String driverclassname = findTestData('Login/Login').getValue(6, 10)

		String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

		return Sql.newInstance(url,username,password,driverclassname)
	}

	//keyword getExcelPath
	@Keyword
	public getExcelPath(String Path){
		String userDir = System.getProperty('user.dir')

		String filePath = userDir + Path

		return filePath
	}
}




