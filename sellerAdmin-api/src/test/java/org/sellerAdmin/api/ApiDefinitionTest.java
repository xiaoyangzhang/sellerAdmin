package org.sellerAdmin.api;


import java.util.List;


import org.junit.Assert;
import org.junit.Test;
import org.yimayhd.sellerAdmin.api.PublishGoodsApi;


import net.pocrd.core.ApiDocumentationHelper;
import net.pocrd.core.ApiManager;
import net.pocrd.define.ApiOpenState;
import net.pocrd.entity.ApiMethodInfo;



public class ApiDefinitionTest {

    public void testApi(ApiManager manager,ApiDocumentationHelper apiDoc,Class c){
        List<ApiMethodInfo> apis = ApiManager.parseApi(c, new Object());
        apiDoc.getDocument(apis.toArray(new ApiMethodInfo[apis.size()]));
        manager.register(apis, ApiOpenState.OPEN_TO_CLIENT);
        System.out.println(apis.size());
    }
    @Test
    public void testApi() {
        try {
            ApiManager manager = new ApiManager();
            ApiDocumentationHelper apiDoc = new ApiDocumentationHelper();
 /*           List<ApiMethodInfo> apis = ApiManager.parseApi(TrackPedometerApi.class, new Object());
            apiDoc.getDocument(apis.toArray(new ApiMethodInfo[apis.size()]));
            manager.register(apis, ApiOpenState.OPEN_TO_CLIENT);
            System.out.println(apis.size());*/

            testApi(manager,apiDoc, PublishGoodsApi.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail("parse api error.");
        }
    }

}
