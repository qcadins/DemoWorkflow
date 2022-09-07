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

import groovy.sql.Sql
import internal.GlobalVariable

public class checkNAP4db {

	@Keyword
	public checkLegaldocument(Sql instance){
		ArrayList<String> legaldocument = new ArrayList<String>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'LEGAL_DOC_TYPE'"), { def row ->
			legaldocument.add(row[0].toUpperCase())
		})
		return legaldocument
	}
	
	@Keyword
	public countDebtorGroup(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_DEBT_GRP"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countDebtorBusinessScale(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SCL"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countCounterpartCategory(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_CNTRPRT"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countSustainableBusiness(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SUSTAIN"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	
	@Keyword
	public countDebtorGroupSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'SLIK_GOL_DEB'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countRatingInstitute(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'SLIK_LMBG_PMRNGKT'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countAffiliateMultifinanceSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'SLIK_HUB_DGN_LJK'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countCSPUSLSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'CSP_USL_AML'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countPaymentType(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'PAYMENT_TYPE_AML'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countBusinessSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'BUSSOURCE_AML'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countDepartmentAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'DEPARTMENT_AML'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countAuthorityAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'AUTHORITY_AML'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	@Keyword
	public countBuildingOwnership(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP'"), { def row ->
			countData = (row[0])
		})
		return countData
	}
}
