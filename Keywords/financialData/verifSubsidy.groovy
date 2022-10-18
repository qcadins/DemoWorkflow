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

public class verifSubsidy {

	@Keyword
	public verifySubsidyDefault(Sql instanceLOS, Sql instanceFOU, String appNo){
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> subsidyFromType = new ArrayList<>()
		ArrayList<String> subsidyFromValue = new ArrayList<>()
		ArrayList<String> subsidyAlloc = new ArrayList<>()
		ArrayList<String> subsidySource = new ArrayList<>()
		ArrayList<String> subsidyType = new ArrayList<>()
		ArrayList<String> subsidyValue = new ArrayList<>()

		String appId, lobCode, supplCode, rsv_field1, mrorderinfocode, mrappsourcecode, isaffiliate, inscobranchcode
		instanceLOS.eachRow(("select app_id, MR_ORDER_INFO_CODE,MR_APP_SOURCE_CODE,lob_code from app where app_no = '"+appNo+"'"), { def row ->
			appId = row[0]
			mrorderinfocode = row[1]
			mrappsourcecode = row[2]
			lobCode = row[3]
		})

		instanceLOS.eachRow(("select IS_AFFILIATE_WITH_MF from APP_CUST where app_id = '"+appId+"'"), { def row ->
			isaffiliate = row[0]
		})

		instanceLOS.eachRow(("select suppl_code, RSV_FIELD_1 from app_asset where app_id = '"+appId+"'"), { def row ->
			supplCode = row[0]
			rsv_field1 = row[1]
		})

		instanceLOS.eachRow(("select insco_branch_code from app_ins_obj where app_id = '"+appId+"'"), { def row ->
			inscobranchcode = row[0]
		})

		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.SubsidyRulePath

		Integer startRow = 15
		//		startRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'Rl.SubsidyMF', "App.MrOrderInfoCode == @val")+2

		def ruleSubsidyMF = findTestData('DownloadRule/SubsidyRuleMF')

		int matchOrderInfo = 0, matchAppSource = 0, matchIsAffiliate = 0, matchLobCode = 0

		for (int i = startRow;i<=ruleSubsidyMF.getRowNumbers();i++){
			if(ruleSubsidyMF.getValue(1,i)==mrorderinfocode || ruleSubsidyMF.getValue(1,i)=="-"){
				matchOrderInfo=1
			}
			else if(ruleSubsidyMF.getValue(1,i)!=mrorderinfocode && ruleSubsidyMF.getValue(1,i)!="" && ruleSubsidyMF.getValue(1,i)!="-"){
				matchOrderInfo=0
			}
			if(ruleSubsidyMF.getValue(2,i)==mrappsourcecode || ruleSubsidyMF.getValue(2,i)=="-"){
				matchAppSource=1
			}
			else if(ruleSubsidyMF.getValue(2,i)!=mrappsourcecode && ruleSubsidyMF.getValue(2,i)!="" && ruleSubsidyMF.getValue(2,i)!="-"){
				matchAppSource=0
			}
			if(ruleSubsidyMF.getValue(3,i)==isaffiliate || ruleSubsidyMF.getValue(3,i)=="-"){
				matchIsAffiliate=1
			}
			else if(ruleSubsidyMF.getValue(3,i)!=isaffiliate && ruleSubsidyMF.getValue(3,i)!="" && ruleSubsidyMF.getValue(3,i)!="-"){
				matchIsAffiliate=0
			}
			if(ruleSubsidyMF.getValue(4,i)==lobCode || ruleSubsidyMF.getValue(4,i)=="-"){
				matchLobCode=1
			}
			else if(ruleSubsidyMF.getValue(4,i)!=lobCode && ruleSubsidyMF.getValue(4,i)!="" && ruleSubsidyMF.getValue(4,i)!="-"){
				matchLobCode=0
			}
			if(((ruleSubsidyMF.getValue(1,i)=="" && matchOrderInfo==1) || matchOrderInfo==1) && ((ruleSubsidyMF.getValue(2,i)=="" && matchAppSource==1) || matchAppSource==1)
			&& ((ruleSubsidyMF.getValue(3,i)=="" && matchIsAffiliate==1) || matchIsAffiliate==1) && ((ruleSubsidyMF.getValue(4,i)=="" && matchLobCode==1) || matchLobCode==1) ){
				subsidyFromType.add(ruleSubsidyMF.getValue(5,i))
				subsidyFromValue.add(ruleSubsidyMF.getValue(6,i))
				subsidyAlloc.add(ruleSubsidyMF.getValue(7,i))
				subsidySource.add(ruleSubsidyMF.getValue(8,i))
				subsidyType.add(ruleSubsidyMF.getValue(9,i))
				subsidyValue.add(ruleSubsidyMF.getValue(10,i))
			}
			else if((ruleSubsidyMF.getValue(1,i)=="" && ruleSubsidyMF.getValue(2,i)=="" && ruleSubsidyMF.getValue(3,i)=="" && ruleSubsidyMF.getValue(4,i)=="" && ruleSubsidyMF.getValue(5,i)=="" && ruleSubsidyMF.getValue(6,i)=="")){
				break
			}
		}


		startRow = 14
		//		startRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'Rl.SubsidyInsco', "AppInsObj.InscoBranchCode == @val")+2

		def ruleSubsidyInsco = findTestData('DownloadRule/SubsidyRuleInsco')

		int matchInsco = 0
		matchLobCode = 0

		for (int i = startRow;i<=ruleSubsidyInsco.getRowNumbers();i++){
			if(ruleSubsidyInsco.getValue(1,i)==inscobranchcode || ruleSubsidyInsco.getValue(1,i)=="-"){
				matchInsco=1
			}
			else if(ruleSubsidyInsco.getValue(1,i)!=inscobranchcode && ruleSubsidyInsco.getValue(1,i)!="" && ruleSubsidyInsco.getValue(1,i)!="-"){
				matchInsco=0
			}
			if(ruleSubsidyInsco.getValue(2,i)==lobCode || ruleSubsidyInsco.getValue(2,i)=="-"){
				matchLobCode=1
			}
			else if(ruleSubsidyInsco.getValue(2,i)!=lobCode && ruleSubsidyInsco.getValue(2,i)!="" && ruleSubsidyInsco.getValue(2,i)!="-"){
				matchLobCode=0
			}
			if(((ruleSubsidyInsco.getValue(1,i)=="" && matchInsco==1) || matchInsco==1) && ((ruleSubsidyInsco.getValue(2,i)=="" && matchLobCode==1) || matchLobCode==1)){
				subsidyFromType.add(ruleSubsidyInsco.getValue(3,i))
				subsidyFromValue.add(checkName(instanceFOU,ruleSubsidyInsco.getValue(4,i)))
				subsidyAlloc.add(ruleSubsidyInsco.getValue(5,i))
				subsidySource.add(ruleSubsidyInsco.getValue(6,i))
				subsidyType.add(ruleSubsidyInsco.getValue(7,i))
				subsidyValue.add(ruleSubsidyInsco.getValue(8,i))
			}
			else if((ruleSubsidyInsco.getValue(1,i)=="" && ruleSubsidyInsco.getValue(2,i)=="" && ruleSubsidyInsco.getValue(3,i)=="" && ruleSubsidyInsco.getValue(4,i)=="" && ruleSubsidyInsco.getValue(5,i)=="" && ruleSubsidyInsco.getValue(6,i)=="")){
				break
			}
		}

		startRow = 14
		//		startRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'Rl.SubsidySuppl', "AppAsset.RsvField1 == @val")+2

		def ruleSubsidySuppl = findTestData('DownloadRule/SubsidyRuleSuppl')

		int matchrsvfield = 0, matchsupplcode = 0
		matchLobCode = 0

		for (int i = startRow;i<=ruleSubsidySuppl.getRowNumbers();i++){
			if(ruleSubsidySuppl.getValue(1,i)==rsv_field1 || ruleSubsidySuppl.getValue(1,i)=="-"){
				matchrsvfield=1
			}
			else if(ruleSubsidySuppl.getValue(1,i)!=rsv_field1 && ruleSubsidySuppl.getValue(1,i)!="" && ruleSubsidySuppl.getValue(1,i)!="-"){
				matchrsvfield=0
			}
			if(ruleSubsidySuppl.getValue(2,i)==supplCode || ruleSubsidySuppl.getValue(2,i)=="-"){
				matchsupplcode=1
			}
			else if(ruleSubsidySuppl.getValue(2,i)!=supplCode && ruleSubsidySuppl.getValue(2,i)!="" && ruleSubsidySuppl.getValue(2,i)!="-"){
				matchsupplcode=0
			}
			if(ruleSubsidySuppl.getValue(3,i)==lobCode || ruleSubsidySuppl.getValue(3,i)=="-"){
				matchLobCode=1
			}
			else if(ruleSubsidySuppl.getValue(3,i)!=lobCode && ruleSubsidySuppl.getValue(3,i)!="" && ruleSubsidySuppl.getValue(3,i)!="-"){
				matchLobCode=0
			}
			if(((ruleSubsidySuppl.getValue(1,i)=="" && matchrsvfield==1) || matchrsvfield==1) && ((ruleSubsidySuppl.getValue(2,i)=="" && matchsupplcode==1) || matchsupplcode==1)
			&& ((ruleSubsidySuppl.getValue(3,i)=="" && matchLobCode==1) || matchLobCode==1)){
				subsidyFromType.add(ruleSubsidySuppl.getValue(4,i))
				subsidyFromValue.add(checkName(instanceFOU,ruleSubsidySuppl.getValue(5,i)))
				subsidyAlloc.add(ruleSubsidySuppl.getValue(6,i))
				subsidySource.add(ruleSubsidySuppl.getValue(7,i))
				subsidyType.add(ruleSubsidySuppl.getValue(8,i))
				subsidyValue.add(ruleSubsidySuppl.getValue(9,i))
			}
			else if((ruleSubsidySuppl.getValue(1,i)=="" && ruleSubsidySuppl.getValue(2,i)=="" && ruleSubsidySuppl.getValue(3,i)=="" && ruleSubsidySuppl.getValue(4,i)=="" && ruleSubsidySuppl.getValue(5,i)=="" && ruleSubsidySuppl.getValue(6,i)=="")){
				break
			}
		}

		result.put("FT",subsidyFromType)
		result.put("FV",subsidyFromValue)
		result.put("Alloc",subsidyAlloc)
		result.put("Source",subsidySource)
		result.put("Type",subsidyType)
		result.put("Value",subsidyValue)
		return result

	}
	@Keyword
	public checkSubsidyValueTypeCode(Sql instanceLOS, String name){
		String Code
		instanceLOS.eachRow(("select REF_MASTER_CODE from ref_master_los where ref_master_type_code = 'SUBSIDY_VALUE_TYPE' AND REF_MASTER_NAME='"+name+"'"), { def row ->
			Code = row[0]
		})
		return Code
	}
	@Keyword
	public checkSubsidyFromTypeCode(Sql instanceLOS, String name){
		String Code
		instanceLOS.eachRow(("select REF_MASTER_CODE from ref_master_los where ref_master_type_code = 'SUBSIDY_FROM_TYPE' AND REF_MASTER_NAME='"+name+"'"), { def row ->
			Code = row[0]
		})
		return Code
	}
	@Keyword
	public checkSubsidyAllocCode(Sql instanceLOS, String name){
		String Code
		instanceLOS.eachRow(("select REF_MASTER_CODE from ref_master_los where ref_master_type_code = 'SUBSIDY_ALLOC' AND REF_MASTER_NAME='"+name+"'"), { def row ->
			Code = row[0]
		})
		return Code
	}
	@Keyword
	public checkSubsidySourceCode(Sql instanceLOS, String name){
		String Code
		instanceLOS.eachRow(("select REF_MASTER_CODE from ref_master_los where ref_master_type_code = 'SUBSIDY_SOURCE' AND REF_MASTER_NAME='"+name+"'"), { def row ->
			Code = row[0]
		})
		return Code
	}

	@Keyword
	public checkName(Sql instanceFOU, String code){
		String name
		instanceFOU.eachRow(("select VENDOR_NAME from vendor where vendor_code = '"+code+"'"), { def row ->
			name = row[0]
		})
		return name
	}
}
