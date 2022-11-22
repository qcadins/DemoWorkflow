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

import internal.GlobalVariable
import groovy.sql.Sql as Sql
public class verifyMatchCopyAppYes {

	@Keyword
	public verifyMatchCopyAppYesNAP2Personal(Sql instance, String copyAppNo, String appNo){
		Boolean isMatch = true
		ArrayList<String> copyappdata = new ArrayList<String>()
		ArrayList<String> appdata = new ArrayList<String>()
		ArrayList<String> insuredBy = new ArrayList<String>()
		instance.eachRow(("select ins_asset_covered_by from app_ins_obj where app_id in (select app_id from app where app_no in('"+copyAppNo+"','"+appNo+"'))"), { def row ->
			insuredBy.add(row[0])
		})

		if(insuredBy.get(0).equalsIgnoreCase(insuredBy.get(1))){
			if(insuredBy.get(0)=="CU"){

				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CUCO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}


				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
		}
		else{
			isMatch=false
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2LifeInsuranceStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2LifeInsuranceStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		return isMatch
	}

	@Keyword
	public verifyMatchCopyAppYesNAP2Company(Sql instance, String copyAppNo, String appNo){

		Boolean isMatch = true
		ArrayList<String> copyappdata = new ArrayList<String>()
		ArrayList<String> appdata = new ArrayList<String>()
		ArrayList<String> insuredBy = new ArrayList<String>()
		instance.eachRow(("select ins_asset_covered_by from app_ins_obj where app_id in (select app_id from app where app_no in('"+copyAppNo+"','"+appNo+"'))"), { def row ->
			insuredBy.add(row[0])
		})

		if(insuredBy.get(0).equalsIgnoreCase(insuredBy.get(1))){
			if(insuredBy.get(0)=="CU"){

				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CUCO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceCustMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}


				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
			else if(insuredBy.get(0)=="CO"){
				copyappdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).NAP2InsuranceMFStoreDB(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}

				copyappdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, copyAppNo)
				appdata = (new dbConnection.CustomerDataVerif()).checkTotalInsurance(instance, appNo)
				for(int i = 0;i<copyappdata.size();i++){
					if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
						isMatch = false
						break
					}
				}
			}
		}
		else{
			isMatch=false
		}


		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2FinancialStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		copyappdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, copyAppNo)
		appdata = (new dbConnection.CustomerDataVerif()).NAP2SubsidyStoreDB(instance, appNo)
		for(int i = 0;i<copyappdata.size();i++){
			if(WebUI.verifyMatch(copyappdata[i],appdata[i],false,FailureHandling.OPTIONAL)==false){
				isMatch = false
				break
			}
		}

		return isMatch
	}
}
