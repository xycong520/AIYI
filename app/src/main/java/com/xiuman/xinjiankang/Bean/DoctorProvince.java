package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：医师所在省市
 * Created by hxy on 2015/11/26.
 */
public class DoctorProvince implements Serializable {

    /**
     * childArea : [{"childId":"402881882ba8753a012ba8d03d870077","childName":"长春市"},{"childId":"402881882ba8753a012ba8d065c00078","childName":"吉林市"},{"childId":"402881882ba8753a012ba8d08acc0079","childName":"四平市"},{"childId":"402881882ba8753a012ba8d0b046007a","childName":"辽源市"},{"childId":"402881882ba8753a012ba8d0d7a4007b","childName":"通化市"},{"childId":"402881882ba8753a012ba8d0fe95007c","childName":"白山市"},{"childId":"402881882ba8753a012ba8d120b3007d","childName":"松原市"},{"childId":"402881882ba8753a012ba8d14d8f007e","childName":"白城市"},{"childId":"402881882ba8753a012ba8d17a4d007f","childName":"延边州"},{"childId":"8a04bc8b509414b10150a30274a102d8","childName":"南关区"},{"childId":"8a04bc8b509414b10150a3029a0902db","childName":"宽城区"},{"childId":"8a04bc8b509414b10150a302bfa302e8","childName":"朝阳区"},{"childId":"8a04bc8b509414b10150a302df7602eb","childName":"二道区"},{"childId":"8a04bc8b509414b10150a303050802f7","childName":"绿园区"},{"childId":"8a04bc8b509414b10150a30328d202f9","childName":"双阳区"},{"childId":"8a04bc8b509414b10150a30353620306","childName":"九台区"},{"childId":"8a04bc8b509414b10150a30377c60309","childName":"榆树市"},{"childId":"8a04bc8b509414b10150a303ad250314","childName":"德惠市"},{"childId":"8a04bc8b509414b10150a303e5600321","childName":"农安县"},{"childId":"8a04bc8b509414b10150a3053a040366","childName":"昌邑区"},{"childId":"8a04bc8b509414b10150a30573a90369","childName":"龙潭区"},{"childId":"8a04bc8b509414b10150a305ae43036d","childName":"船营区"},{"childId":"8a04bc8b509414b10150a305dc360371","childName":"丰满区"},{"childId":"8a04bc8b509414b10150a30601940375","childName":"蛟河市"},{"childId":"8a04bc8b509414b10150a30629bd0377","childName":"桦甸市"},{"childId":"8a04bc8b509414b10150a3065a67037b","childName":"舒兰市"},{"childId":"8a04bc8b509414b10150a3067dac037f","childName":"磐石市"},{"childId":"8a04bc8b509414b10150a3069f9c0383","childName":"永吉县"},{"childId":"8a04bc8b509414b10150a307dbb1039a","childName":"铁西区"},{"childId":"8a04bc8b509414b10150a3080613039e","childName":"铁东区"},{"childId":"8a04bc8b509414b10150a3082ddc03a1","childName":"公主岭市"},{"childId":"8a04bc8b509414b10150a3084b2903a3","childName":"双辽市"},{"childId":"8a04bc8b509414b10150a308699403a6","childName":"梨树县"},{"childId":"8a04bc8b509414b10150a30892d503a8","childName":"伊通满族自治县"},{"childId":"8a04bc8b509414b10150a309242203ad","childName":"龙山区"},{"childId":"8a04bc8b509414b10150a30955e803b1","childName":"西安区"},{"childId":"8a04bc8b509414b10150a30975d203b3","childName":"东丰县"},{"childId":"8a04bc8b509414b10150a309973a03b4","childName":"东辽县"},{"childId":"8a04bc8b509414b10150a30a1e7503b6","childName":"东昌区"},{"childId":"8a04bc8b509414b10150a30a470603bf","childName":"二道江区"},{"childId":"8a04bc8b509414b10150a30a694203c1","childName":"梅河口市"},{"childId":"8a04bc8b509414b10150a30a973603c6","childName":"集安市"},{"childId":"8a04bc8b509414b10150a30abe8e03c9","childName":"通化县"},{"childId":"8a04bc8b509414b10150a30ae23603cb","childName":"辉南县"},{"childId":"8a04bc8b509414b10150a30b08e803cf","childName":"柳河县"},{"childId":"8a04bc8b509414b10150a30b7b8503d9","childName":"浑江区"},{"childId":"8a04bc8b509414b10150a30bab5a03db","childName":"江源区"},{"childId":"8a04bc8b509414b10150a30bd97103dd","childName":"临江市"},{"childId":"8a04bc8b509414b10150a30bfd7e03de","childName":"抚松县"},{"childId":"8a04bc8b509414b10150a30c1ddc03df","childName":"靖宇县"},{"childId":"8a04bc8b509414b10150a30c47ad03e3","childName":"长白朝鲜族自治县"},{"childId":"8a04bc8b509414b10150a30c8efd03e8","childName":"宁江区"},{"childId":"8a04bc8b509414b10150a30cbbad03ec","childName":"扶余市"},{"childId":"8a04bc8b509414b10150a30cf76c03ef","childName":"前郭尔罗斯蒙古族自治县"},{"childId":"8a04bc8b509414b10150a30d242b03f3","childName":"长岭县"},{"childId":"8a04bc8b509414b10150a30d4b1503f6","childName":"乾安县"},{"childId":"8a04bc8b509414b10150a30d9c4c03f7","childName":"洮北区"},{"childId":"8a04bc8b509414b10150a30dd03403f8","childName":"洮南市"},{"childId":"8a04bc8b509414b10150a30df96403fb","childName":"大安市"},{"childId":"8a04bc8b509414b10150a30e25a403fe","childName":"镇赉县"},{"childId":"8a04bc8b509414b10150a30e4b830401","childName":"通榆县"},{"childId":"8a04bc8b509414b10150a30e9446040b","childName":"延吉市"},{"childId":"8a04bc8b509414b10150a30eb9730411","childName":"图们市"},{"childId":"8a04bc8b509414b10150a30edf870415","childName":"敦化市"},{"childId":"8a04bc8b509414b10150a30f09b70419","childName":"珲春市"},{"childId":"8a04bc8b509414b10150a30f29dd041b","childName":"龙井市"},{"childId":"8a04bc8b509414b10150a30f484e041e","childName":"和龙市"},{"childId":"8a04bc8b509414b10150a30f6be60423","childName":"汪清县"},{"childId":"8a04bc8b509414b10150a30f91ac0426","childName":"安图县"}]
     * rootId : 402881882ba8753a012ba8d010f90076
     * rootName : 吉林省
     */

    private String rootId;
    private String rootName;
    /**
     * childId : 402881882ba8753a012ba8d03d870077
     * childName : 长春市
     */

    private List<DoctorCity> childArea;

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public void setChildArea(List<DoctorCity> childArea) {
        this.childArea = childArea;
    }

    public String getRootId() {
        return rootId;
    }

    public String getRootName() {
        return rootName;
    }

    public List<DoctorCity> getChildArea() {
        return childArea;
    }

}
