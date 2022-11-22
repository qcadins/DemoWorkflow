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
import internal.GlobalVariable

public class checkNAP4db {

	@Keyword
	public checkLegaldocument(Sql instance){
		ArrayList<String> legaldocument = new ArrayList<String>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'LEGAL_DOC_TYPE' AND IS_ACTIVE = 1"), { def row ->
			legaldocument.add(row[0].toUpperCase())
		})
		return legaldocument
	}

	@Keyword
	public checkAddressTypePersonal(Sql instance){
		ArrayList<String> AddressType = new ArrayList<String>()
		instance.eachRow(("select DESCR from REF_MASTER WITH(NOLOCK) where REF_MASTER_TYPE_CODE = 'CUST_ADDR_TYPE' and IS_ACTIVE = '1' and MAPPING_CODE in ('personal', 'all') and MASTER_CODE not in (SELECT value FROM (select gs_value from GENERAL_SETTING where gs_code = 'FILTER_ADDR') as excludeSetting CROSS APPLY STRING_SPLIT(excludeSetting.gs_value, ';'))"), { def row ->
			AddressType.add(row[0].toUpperCase())
		})
		return AddressType
	}

	@Keyword
	public checkAddressTypeCompany(Sql instance){
		ArrayList<String> AddressType = new ArrayList<String>()
		instance.eachRow(("select DESCR from REF_MASTER WITH(NOLOCK) where REF_MASTER_TYPE_CODE = 'CUST_ADDR_TYPE' and IS_ACTIVE = '1' and MAPPING_CODE in ('company', 'all') and MASTER_CODE not in (SELECT value FROM (select gs_value from GENERAL_SETTING where gs_code = 'FILTER_ADDR') as excludeSetting CROSS APPLY STRING_SPLIT(excludeSetting.gs_value, ';')) AND DESCR NOT IN('Company')"), { def row ->
			AddressType.add(row[0].toUpperCase())
		})
		return AddressType
	}


	@Keyword
	public checkOwnership(Sql instance){
		ArrayList<String> Ownership = new ArrayList<String>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND IS_ACTIVE = 1"), { def row ->
			Ownership.add(row[0].toUpperCase())
		})
		return Ownership
	}

	@Keyword
	public countDebtorGroup(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_DEBT_GRP WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countDebtorBusinessScale(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SCL WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countCounterpartCategory(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_CNTRPRT WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countSustainableBusiness(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SUSTAIN WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}


	@Keyword
	public countDebtorGroupSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_GOL_DEB' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countRatingInstitute(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_LMBG_PMRNGKT' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countAffiliateMultifinanceSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_HUB_DGN_LJK' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countCSPUSLSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'CSP_USL_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countPaymentType(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'PAYMENT_TYPE_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countBusinessSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUSSOURCE_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countDepartmentAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'DEPARTMENT_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countAuthorityAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'AUTHORITY_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countBuildingOwnership(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countAttributeListCompany(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_ATTR WITH(NOLOCK) where ATTR_GROUP = 'CUST_COMPANY_OTH' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	@Keyword
	public countAttributeListPersonal(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_ATTR WITH(NOLOCK) where ATTR_GROUP = 'CUST_PERSONAL_OTH' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}
}
