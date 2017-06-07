package zhengw.confmgr.service;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import zhengw.confmgr.bean.Tuple;
import zhengw.confmgr.service.operator.BaseOperator;

public abstract class BaseService {

	@Autowired
	protected BeanFactory beanFactory;

	protected Tuple<Boolean, String> exeOperate(BaseOperator operator) {

		Tuple<Boolean, String> operateResult = operator.Operate();
		if (!operateResult.getItem1()) {
			throw new RuntimeException(operateResult.getItem2());
		}

		return operateResult;
	}
}
