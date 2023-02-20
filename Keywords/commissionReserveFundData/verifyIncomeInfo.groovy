package commissionReserveFundData

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
import java.util.regex.Pattern
import java.util.regex.Matcher
public class verifyIncomeInfo {

	//keyword verify income info amount rule based
	@Keyword
	public verifyIncomeInfoAmtRuleBased(Sql instanceLOS, String appNo){
		String lobCode
		instanceLOS.eachRow(("select BIZ_TEMPLATE_CODE from app WITH(NOLOCK) WHERE APP_NO='"+appNo+"'"), { def row ->
			lobCode = row[0]
		})
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> refundAllocationFrom = new ArrayList<>()
		ArrayList<String> refundAmt = new ArrayList<>()
		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.MaxRefundRulePath
		def ruleIncomeInfo = findTestData('DownloadRule/RefundComponentRule')
		Integer lobcodeRow = -1
		lobcodeRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'RefundComponent', lobCode)+1
		int match = 0
		for(int i=lobcodeRow;i<=ruleIncomeInfo.getRowNumbers();i++){
			if(ruleIncomeInfo.getValue(1,i)!=lobCode&&ruleIncomeInfo.getValue(1,i)!=""){
				match=0
			}
			if(ruleIncomeInfo.getValue(1,i)==lobCode||match==1){
				if(match==0){
					match=1
				}
				refundAllocationFrom.add(ruleIncomeInfo.getValue(2,i))
				refundAmt.add(ruleIncomeInfo.getValue(3,i))
				println(ruleIncomeInfo.getValue(3,i))
			}
			if(ruleIncomeInfo.getValue(1,i)==""&&ruleIncomeInfo.getValue(2,i)==""){
				break
			}
		}

		for(int j = 0;j<refundAllocationFrom.size();j++){
			//jika string mengandung angka
			if(refundAmt[j].matches(".*\\d.*")){
				Matcher m = Pattern.compile("\\d").matcher(refundAmt[j])
				m.find()
				refundAmt[j] = refundAmt[j].substring(refundAmt[j].indexOf(m.group()))
			}
			//Jika string tidak mengandung angka
			else{
				refundAmt[j]="1"
			}
			refundAllocationFrom[j] = refundAllocationFrom[j].replace("_"," ")

		}

		result.put("From",refundAllocationFrom)
		result.put("Amt",refundAmt)
		return result
	}

	//keyword check admin fee value
	@Keyword
	public checkAdminFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = af.app_id where mr_fee_type_code ='ADMIN' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	//keyword check provision fee value
	@Keyword
	public checkProvisionFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on af.app_id = a.app_id where mr_fee_type_code ='PROVISION' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	//keyword check diff rate amt value
	@Keyword
	public checkDiffRateAmtValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select CASE WHEN DIFF_RATE_AMT<0 THEN 0 ELSE DIFF_RATE_AMT END from app_fin_data afd WITH(NOLOCK) join app a WITH(NOLOCK) on afd.app_id = a.app_id where app_no = '"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	//keyword check other fee value
	@Keyword
	public checkOtherFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = af.app_id where mr_fee_type_code ='OTHER' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	//keyword check ins value
	@Keyword
	public checkInsValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select total_ins_cust_amt, total_ins_insco_amt from app_fin_data afd WITH(NOLOCK) join app a WITH(NOLOCK) on afd.app_id = a.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		if(value<0){
			value=0
		}
		return value
	}

	//keyword check life ins value
	@Keyword
	public checkLifeInsValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select TOTAL_LIFE_INS_CUST_AMT, [TOTAL_LIFE_INS_INSCO_AMT ] from app_fin_data afd WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = afd.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		if(value<0){
			value=0
		}
		return value
	}

	//keyword untuk cek nilai max refund based on rule
	@Keyword
	public verifyMaxAllocation(Sql instanceLOS, String appNo){
		String lobCode
		instanceLOS.eachRow(("select BIZ_TEMPLATE_CODE from app WITH(NOLOCK) WHERE APP_NO='"+appNo+"'"), { def row ->
			lobCode = row[0]
		})

		ArrayList<String> maxrefundAmt = new ArrayList<>()
		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.MaxRefundRulePath
		def ruleIncomeInfo = findTestData('DownloadRule/MaxRefundRule')
		Integer lobcodeRow = -1
		lobcodeRow = (new customizeKeyword.getRow()).getExcelRow(filePath, 'MaxRefund', lobCode)+1
		int match = 0
		for(int i=lobcodeRow;i<=ruleIncomeInfo.getRowNumbers();i++){
			if(ruleIncomeInfo.getValue(1,i)!=lobCode&&ruleIncomeInfo.getValue(1,i)!=""){
				match=0
			}
			if(ruleIncomeInfo.getValue(1,i)==lobCode||match==1){
				if(match==0){
					match=1
				}
				maxrefundAmt.add(ruleIncomeInfo.getValue(2,i))
			}
			if(ruleIncomeInfo.getValue(1,i)==""&&ruleIncomeInfo.getValue(2,i)==""){
				break
			}
		}

		ArrayList<Double> numbers = new ArrayList<>();

		Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(maxrefundAmt[0])
		while(m.find()){
			println(Double.valueOf(m.group())+"abc")
			numbers.add(Double.valueOf(m.group()))
		}


		Integer totalMaxAllocated = 0
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = af.app_id where mr_fee_type_code ='OTHER' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		totalMaxAllocated+=value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on af.app_id = a.app_id where mr_fee_type_code ='PROVISION' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		totalMaxAllocated+=value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE af WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = af.app_id where mr_fee_type_code ='ADMIN' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		totalMaxAllocated+=value
		instanceLOS.eachRow(("select TOTAL_LIFE_INS_CUST_AMT, [TOTAL_LIFE_INS_INSCO_AMT ] from app_fin_data afd WITH(NOLOCK) join app a WITH(NOLOCK) on a.app_id = afd.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		totalMaxAllocated+=value
		instanceLOS.eachRow(("select total_ins_cust_amt, total_ins_insco_amt from app_fin_data afd WITH(NOLOCK) join app a WITH(NOLOCK) on afd.app_id = a.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		totalMaxAllocated+=value
		instanceLOS.eachRow(("select CONVERT(INT,total_interest_amt) from app_fin_data afd join app a on afd.app_id = a.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]
		})
		
		totalMaxAllocated+=value
		println(totalMaxAllocated)
		if(GlobalVariable.LOB == 'CF4W'){
			totalMaxAllocated-=(numbers[0])
			totalMaxAllocated*=numbers[1]
		}else if(GlobalVariable.LOB == 'FL4W'){
			totalMaxAllocated*=numbers[0]
		}
		println(totalMaxAllocated)
		return totalMaxAllocated
	}
}