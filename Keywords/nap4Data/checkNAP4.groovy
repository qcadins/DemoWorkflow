package nap4Data

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

public class checkNAP4 {

	//keyword check legal document DDL
	@Keyword
	public checkLegaldocument(Sql instance){
		ArrayList<String> legaldocument = new ArrayList<String>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'LEGAL_DOC_TYPE' AND IS_ACTIVE = 1"), { def row ->
			legaldocument.add(row[0].toUpperCase())
		})
		return legaldocument
	}

	//keyword check addresstype personal DDL
	@Keyword
	public checkAddressTypePersonal(Sql instance){
		ArrayList<String> AddressType = new ArrayList<String>()
		instance.eachRow(("select DESCR from REF_MASTER WITH(NOLOCK) where REF_MASTER_TYPE_CODE = 'CUST_ADDR_TYPE' and IS_ACTIVE = '1' and MAPPING_CODE in ('personal', 'all') and MASTER_CODE not in (SELECT value FROM (select gs_value from GENERAL_SETTING where gs_code = 'FILTER_ADDR') as excludeSetting CROSS APPLY STRING_SPLIT(excludeSetting.gs_value, ';'))"), { def row ->
			AddressType.add(row[0].toUpperCase())
		})
		return AddressType
	}

	//keyword check addresstype company DDL
	@Keyword
	public checkAddressTypeCompany(Sql instance){
		ArrayList<String> AddressType = new ArrayList<String>()
		instance.eachRow(("select DESCR from REF_MASTER WITH(NOLOCK) where REF_MASTER_TYPE_CODE = 'CUST_ADDR_TYPE' and IS_ACTIVE = '1' and MAPPING_CODE in ('company', 'all') and MASTER_CODE not in (SELECT value FROM (select gs_value from GENERAL_SETTING where gs_code = 'FILTER_ADDR') as excludeSetting CROSS APPLY STRING_SPLIT(excludeSetting.gs_value, ';')) AND DESCR NOT IN('Company')"), { def row ->
			AddressType.add(row[0].toUpperCase())
		})
		return AddressType
	}

	//keyword check ownership DDL
	@Keyword
	public checkOwnership(Sql instance){
		ArrayList<String> Ownership = new ArrayList<String>()
		instance.eachRow(("SELECT DESCR FROM REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND IS_ACTIVE = 1"), { def row ->
			Ownership.add(row[0].toUpperCase())
		})
		return Ownership
	}

	//keyword count debtorgroup total data
	@Keyword
	public countDebtorGroup(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_DEBT_GRP WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count debtor business scale total data
	@Keyword
	public countDebtorBusinessScale(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SCL WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count counterpart category total data
	@Keyword
	public countCounterpartCategory(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_CNTRPRT WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count sustainable business total data
	@Keyword
	public countSustainableBusiness(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from LBPPMS_BIZ_SUSTAIN WITH(NOLOCK)"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count debtorgroupSLIK total data
	@Keyword
	public countDebtorGroupSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_GOL_DEB' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count rating institute total data
	@Keyword
	public countRatingInstitute(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_LMBG_PMRNGKT' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count affiliate with multifinance total data
	@Keyword
	public countAffiliateMultifinanceSLIK(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'SLIK_HUB_DGN_LJK' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count CSPUSL Source AML total data
	@Keyword
	public countCSPUSLSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'CSP_USL_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count payment total data
	@Keyword
	public countPaymentType(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'PAYMENT_TYPE_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count business source AML total data
	@Keyword
	public countBusinessSourceAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUSSOURCE_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count department AML total data
	@Keyword
	public countDepartmentAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'DEPARTMENT_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count authority AML total data
	@Keyword
	public countAuthorityAML(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'AUTHORITY_AML' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count building ownership total data
	@Keyword
	public countBuildingOwnership(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_MASTER WITH(NOLOCK) WHERE REF_MASTER_TYPE_CODE = 'BUILDING_OWNERSHIP' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count attribute list company total data
	@Keyword
	public countAttributeListCompany(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_ATTR WITH(NOLOCK) where ATTR_GROUP = 'CUST_COMPANY_OTH' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}

	//keyword count attribute list personal total data
	@Keyword
	public countAttributeListPersonal(Sql instance){
		Integer countData
		instance.eachRow(("select count(*) from REF_ATTR WITH(NOLOCK) where ATTR_GROUP = 'CUST_PERSONAL_OTH' AND IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	//keyword check cust asset DDL
	@Keyword
	public checkcustAsset(Sql instance){
		ArrayList<String> value = new ArrayList<String>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'CUST_ASSET_TYPE' and IS_ACTIVE = 1"), { def row ->
			value.add(row[0].toUpperCase())
		})
		return value
	}
	
	//keyword check job position DDL
	@Keyword
	public checkjobPosition(Sql instance){
		ArrayList<String> value = new ArrayList<String>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'JOB_POSITION' and IS_ACTIVE = 1"), { def row ->
			value.add(row[0].toUpperCase())
		})
		return value
	}
	
	//keyword check job status DDL
	@Keyword
	public checkjobStatus(Sql instance){
		ArrayList<String> value = new ArrayList<String>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'JOB_STAT' and IS_ACTIVE = 1"), { def row ->
			value.add(row[0].toUpperCase())
		})
		return value
	}
	
	//keyword check company Scale status DDL
	@Keyword
	public checkcompanyScale(Sql instance){
		ArrayList<String> value = new ArrayList<String>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'COY_SCALE' and IS_ACTIVE = 1"), { def row ->
			value.add(row[0].toUpperCase())
		})
		return value
	}
	
	//keyword check company relationship status DDL
	@Keyword
	public checkcompanyrelationship(Sql instance){
		ArrayList<String> value = new ArrayList<String>()
		instance.eachRow(("SELECT UPPER(DESCR) FROM REF_MASTER WHERE REF_MASTER_TYPE_CODE = 'CUST_COMPANY_RELATIONSHIP' and IS_ACTIVE = 1"), { def row ->
			value.add(row[0].toUpperCase())
		})
		return value
	}
}