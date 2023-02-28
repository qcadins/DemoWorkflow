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

	//Keyword untuk mencari nama-nama asset document yang muncul pada tab asset data
	@Keyword
	public checkAssetDocument(Sql instance, String fullAssetCode){
		ArrayList<String> documentName = new ArrayList<String>()

		instance.eachRow(("select rad.ASSET_DOC_NAME from ASSET_MASTER am with(nolock) join ASSET_DOC_LIST adl with(nolock) on am.ASSET_TYPE_ID = adl.ASSET_TYPE_ID join REF_ASSET_DOC rad with(nolock) on rad.REF_ASSET_DOC_ID = adl.REF_ASSET_DOC_ID where am.FULL_ASSET_CODE = '"+fullAssetCode+"' and am.PARENT_ID is not null and rad.IS_ACTIVE = 1 ORDER BY rad.ASSET_DOC_NAME"), { def row ->
			documentName.add(row[0].toUpperCase())

		})

		return documentName
	}

	//Keyword untuk mencari nama-nama Collateral document yang muncul pada tab asset data
	@Keyword
	public checkCollateralDocument(Sql instance, String collateraltype){
		ArrayList<String> documentName = new ArrayList<String>()

		instance.eachRow(("SELECT ASSET_DOC_NAME FROM ASSET_DOC_LIST adl JOIN REF_ASSET_DOC rad ON adl.REF_ASSET_DOC_ID = rad.REF_ASSET_DOC_ID JOIN ASSET_TYPE at ON at.ASSET_TYPE_ID = adl.ASSET_TYPE_ID WHERE at.ASSET_TYPE_CODE = '"+ collateraltype +"' and adl.IS_ACTIVE = 1 and rad.IS_ACTIVE = 1 ORDER BY ASSET_DOC_NAME"), { def row ->
			documentName.add(row[0].toUpperCase())

		})

		return documentName
	}
}
