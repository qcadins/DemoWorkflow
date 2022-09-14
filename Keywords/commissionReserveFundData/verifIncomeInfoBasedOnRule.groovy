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
public class verifIncomeInfoBasedOnRule {
	@Keyword
	public verifIncomeInfoAmtRuleBased(Sql instanceLOS, String appNo){
		String lobCode
		instanceLOS.eachRow(("select BIZ_TEMPLATE_CODE from app WHERE APP_NO='"+appNo+"'"), { def row ->
			lobCode = row[0]
		})
		HashMap<String,ArrayList> result = new HashMap<>()
		ArrayList<String> refundAllocationFrom = new ArrayList<>()
		ArrayList<String> refundAmt = new ArrayList<>()
		String userDir = System.getProperty('user.dir')
		String filePath = userDir+GlobalVariable.MaxRefundRulePath
		def ruleIncomeInfo = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/RefundComponentRule')
		Integer lobcodeRow = -1
		lobcodeRow = (new excelGetRow.getRow()).getExcelRow(filePath, 'RefundComponent', lobCode)+1
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
			}
			if(ruleIncomeInfo.getValue(1,i)==""&&ruleIncomeInfo.getValue(2,i)==""){
				break
			}
		}

		for(int j = 0;j<refundAllocationFrom.size();j++){
			Matcher m = Pattern.compile("\\d").matcher(refundAmt[j])
			m.find()
			refundAllocationFrom[j] = refundAllocationFrom[j].replace("_"," ")
			refundAmt[j] = refundAmt[j].substring(refundAmt[j].indexOf(m.group()))
		}

		result.put("From",refundAllocationFrom)
		result.put("Amt",refundAmt)
		return result
	}

	@Keyword
	public checkAdminFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE a join app b on a.app_id = b.app_id where mr_fee_type_code ='ADMIN' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	@Keyword
	public checkProvisionFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE a join app b on a.app_id = b.app_id where mr_fee_type_code ='PROVISION' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	@Keyword
	public checkDiffRateAmtValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select DIFF_RATE_AMT from app_fin_data a join app b on a.app_id = b.app_id where app_no = '"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	@Keyword
	public checkOtherFeeValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select app_fee_amt from APP_FEE a join app b on a.app_id = b.app_id where mr_fee_type_code ='OTHER' and app_no='"+appNo+"'"), { def row ->
			value = row[0]
		})
		return value
	}

	@Keyword
	public checkInsValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select total_ins_cust_amt, total_ins_insco_amt from app_fin_data a join app b on a.app_id = b.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		if(value<0){
			value=0
		}
		return value
	}

	@Keyword
	public checkLifeInsValue(Sql instanceLOS, String appNo){
		BigDecimal value
		instanceLOS.eachRow(("select TOTAL_LIFE_INS_CUST_AMT, [TOTAL_LIFE_INS_INSCO_AMT ] from app_fin_data a join app b on a.app_id = b.app_id where APP_NO='"+appNo+"'"), { def row ->
			value = row[0]-row[1]
		})
		if(value<0){
			value=0
		}
		return value
	}
}