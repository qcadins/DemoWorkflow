package insuranceData

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

public class checkInsRateBase {

	@Keyword
	public checkInsuranceRateBasedOn(Sql instance){
		String gs_value

		instance.eachRow(("select gs_value from GENERAL_SETTING where gs_code = 'INS_RATE_BASE_ON'"), { def row ->
			gs_value = row[0]
		})

		return gs_value
	}

	@Keyword
	public checkInsuredbyDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'INSURED_BY' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkCoverPeriodDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'INS_COVER_PERIOD' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkPaymentTypeDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'PAY_PERIOD_TO_INSCO' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}

	@Keyword
	public checkMainCVGDDL(Sql instance){
		String value
		ArrayList<String> listValue = new ArrayList<>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'INS_MAIN_CVG_TYPE' and IS_ACTIVE = 1"), { def row ->
			value = (row[0])
			listValue.add(value)
		})
		return listValue
	}
}
