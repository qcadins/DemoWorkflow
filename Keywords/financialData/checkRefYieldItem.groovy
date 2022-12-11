package financialData

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

public class checkRefYieldItem {

	@Keyword
	public checkInsCust(Sql instanceLOS, String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'INS_TO_CUST' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})
		if(isactive==true){
			instanceLOS.eachRow(("select total_cust_premi_amt from app_ins ai with(nolock) join app a with(nolock) on ai.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
				value = row[0]
			})
		}

		return value
	}

	@Keyword
	public checkInsInsco(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'INS_TO_INSCO' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			instanceLOS.eachRow(("select total_insco_premi_amt from app_ins ai with(nolock) join app a with(nolock) on ai.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
				value = row[0]
			})
		}

		return value
	}

	@Keyword
	public checkLifeInsCust(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'LFI_TO_CUST' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			instanceLOS.eachRow(("select total_premi_to_cust from APP_LIFE_INS_H alih with(nolock) join app a with(nolock) on alih.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
				value = row[0]
			})
		}

		return value
	}

	@Keyword
	public checkLifeInsInsco(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'LFI_TO_INSCO' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			instanceLOS.eachRow(("select total_premi_from_insco from APP_LIFE_INS_H alih with(nolock) join app a with(nolock) on alih.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
				value = row[0]
			})
		}

		return value
	}

	@Keyword
	public checkAdminFee(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'ADMIN' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 21)
		}

		return value
	}

	@Keyword
	public checkFiduciaFee(Sql instanceLOS,String appNo){
		def isactive
		String value = 1
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'FIDUCIA' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 25)
		}

		return value
	}

	@Keyword
	public checkProvisionFee(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'PROVISION' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})

		if(isactive==true){
			value = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
					'value', FailureHandling.OPTIONAL).replace(',', '')
		}

		return value
	}

	@Keyword
	public checkOtherFee(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'OTHER' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})


		if(isactive==true){
			value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 24)
		}

		return value
	}

	@Keyword
	public checkNotaryFee(Sql instanceLOS,String appNo){
		def isactive
		String value = "0"
		instanceLOS.eachRow(("select is_active from ref_yield_item where MR_YIELD_ITEM_CODE = 'NOTARY' and biz_tmplt_code = 'CF4W'"), { def row ->
			isactive = row[0]
		})


		if(isactive==true){
			value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm, 23)
		}

		return value
	}

	@Keyword
	public checkLifeInsuranceCapitalize(Sql instanceLOS,String appNo){
		String value
		instanceLOS.eachRow(("select TOTAL_LIFE_INS_CPTLZ_AMT from APP_LIFE_INS_H alih with(nolock) join app a with(nolock) on alih.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
			value = row[0]
		})

		return value
	}
}
