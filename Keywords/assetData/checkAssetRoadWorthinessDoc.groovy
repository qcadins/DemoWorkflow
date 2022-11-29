package assetData

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
import internal.GlobalVariable

public class checkAssetRoadWorthinessDoc {

	//keyword check road wothiness document
	@Keyword
	public checkRWD(Sql instanceLOS, String appNo){
		String attrVal
		instanceLOS.eachRow(("SELECT ATTR_VALUE FROM APP_ASSET_ATTR WITH(NOLOCK) WHERE APP_ASSET_ID = (SELECT APP_ASSET_ID FROM APP_ASSET WHERE APP_ID = (SELECT APP_ID FROM APP WHERE APP_NO = '"+appNo+"') AND ASSET_ATTR_CODE = 'ROAD_WORTHINESS_DOC')"), { def row ->
			attrVal = row[0]
		})
		return attrVal
	}
}
