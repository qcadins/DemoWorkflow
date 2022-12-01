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
import groovy.sql.Sql as Sql
import internal.GlobalVariable

public class checkAssetDoc {

	@Keyword
	public checkAssetDocument(Sql instance, String fullAssetCode){
		ArrayList<String> documentName = new ArrayList<String>()
		instance.eachRow(("select rad.ASSET_DOC_NAME from ASSET_MASTER am with(nolock) join ASSET_DOC_LIST adl with(nolock) on am.ASSET_TYPE_ID = adl.ASSET_TYPE_ID join REF_ASSET_DOC rad with(nolock) on rad.REF_ASSET_DOC_ID = adl.REF_ASSET_DOC_ID where am.FULL_ASSET_CODE = '"+fullAssetCode+"' and am.PARENT_ID is not null"), { def row ->
			documentName.add(row[0].toUpperCase())
		})
		return documentName
	}
}
