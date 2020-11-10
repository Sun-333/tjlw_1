/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.uestc.tjlw.common.cocurrent;


public interface CallbackTask<R> {

    R execute() throws Exception;

    void onBack(R r);

    void onException(Throwable t);
}
