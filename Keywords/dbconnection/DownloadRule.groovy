package dbconnection

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
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable
import java.io.File
import java.nio.file.*;
import java.io.IOException;
public class DownloadRule {
	@Keyword
	def setDownloadPath() {

		HashMap<Object, String> chromePrefs = new HashMap<Object, String>();


		chromePrefs.put("download.default_directory",System.getProperty('user.dir') + "\\Rule")

		RunConfiguration.setWebDriverPreferencesProperty("prefs", chromePrefs)
	}

	@Keyword
	def deleteFile(String filename){
		File dir = new File(System.getProperty('user.dir') + "\\Rule");

		//Getting the list of all the files in the specific directory
		File[] fList = dir.listFiles();

		for (File f : fList) {
			//checking the extension of the file with endsWith method.
			if (f.getName().equals(filename)) {
				f.delete();

			}
		}
	}

	@Keyword
	def renameFile(String filename, String newname){
		File file = new File(System.getProperty('user.dir') + "\\Rule\\"+filename);
		File rename = new File(System.getProperty('user.dir') + "\\Rule\\"+newname);
		file.renameTo(rename)

	}
}
