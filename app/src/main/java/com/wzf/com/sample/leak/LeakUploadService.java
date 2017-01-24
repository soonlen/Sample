package com.wzf.com.sample.leak;
/*

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;
import com.wzf.com.sample.util.L;

*/
/**
 * Created by soonlen on 2017/1/24.
 *//*


public class LeakUploadService extends DisplayLeakService{

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
//        super.afterDefaultHandling(heapDump, result, leakInfo);
        L.e("================================");
        if(!result.leakFound||result.excludedLeak) {
            return;
        }
        L.e("泄露的信息：\n"+leakInfo);
    }
}
*/
