package com.jeesite.modules.history.form.constructor;

import com.jeesite.modules.history.entity.HtHistory;
import com.jeesite.modules.history.form.FormData;

public interface FormConstructor<t extends FormData> {
    t build(HtHistory htHistory);
}
