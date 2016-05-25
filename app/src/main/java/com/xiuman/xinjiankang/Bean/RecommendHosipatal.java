package com.xiuman.xinjiankang.Bean;

import java.io.Serializable;

/**
 * 描述：推荐医院
 * Created by hxy on 2015/8/30.
 */
public class RecommendHosipatal implements Serializable {

    private static final long serialVersionUID = 768809353954970257L;
    /**
     * distance : null
     * address : 广州市荔湾区多宝路63号
     * hospitaId : 402890084f73150b014f73adb3c80056
     * name : 广州医科大学附属第三医院
     * introduce : 广州医科大学附属第三医院前身是广州市第二人民医院，创建于1899年，最早为美国基督教长老会创办的广东女医学堂及所属赠医所，后为柔济医院，是广州地区一所综合性“三级甲等”医院。医院内设有设施一流的广州市妇产科研究所、广州市重症孕产妇救治中心，医院是全国卫生系统先进单位、国家级爱婴医院、省百家文明医院和广州市文明医院。2006年5月，广州市第二人民医院变更为广州医科大学附属第三医院，同年底成立广州医学院第三临床医院。医院座落在广州市荔湾区，毗邻风景秀丽的荔湾湖，占地总面积28385m2，医疗业务建筑面积约10万m2。现拥有各级卫生技术人员近1300名，其中副高以上职称177人，硕士生导师45人，博士生导师2人，博士硕士209人，享受国务院特殊专家津贴1人。年门诊量达100万人次，编制床位1000张，年收治住院病人逾2.5万人次。 拥有一批先进的医疗设备如核磁共振、螺旋CT机、ECT机、DSACRDR、C臂X光机、全国首台第三代电磁导航系统、彩色心脏腹部B超、实时三维彩色超声仪、子宫内膜微波治疗仪、体外震波碎石机、腹腔镜、小肠镜超声内镜、中央心电监护系统、全自动生化分析仪、现代化静脉配置中心等，总值逾2.3亿元，能充分适应现代临床诊治与医学研究发展的需要。目前，医院的各专业门类设置较为齐全，设有34个临床科室和12个医技科室，开设专科门诊100多个，专家门诊150多个。妇产科是广东省卫生厅特色专科。设在院内的广州市妇产科研究所，主要从事重症孕产妇临床救治与围产医学、生殖与遗传、妇科肿瘤及产前诊断等工作。形成重症孕产妇综合救治、人类辅助生殖、妇科肿瘤诊治、妇科腔镜及盆底诊治四大技术特色，已跨入国内先进水平，是经卫生部批准可以开展人类辅助生殖技术的广州最早的两家医院之一。广州市政府实施《中国21世纪议程广州行动方案》优先发展项目──“广州市重症孕产妇救治中心”于1998年成立并设在我院，主要负责广州地区、邻近地区甚至更远城市重症孕产妇的救治工作，为降低广州地区孕产妇死亡率和新生儿死亡率作出了贡献。医院秉承“柔心济世，尚道精医”的百年传统，不断创新技术，使专业水平迈上新台阶。例如近年来妇科在广州地区率先开展子宫内膜癌及卵巢癌腹主动脉旁淋巴清扫术；开展盆底功能重建治疗；产科研制的“阴道助产仰伸掌”获得国家实用新型专利；儿科设立了广东省PICC培训基地；新生儿科开展了脐静脉置管术的配合；肿瘤科开展了实时超声引导下乳腺肿块螺旋切除术；骨外科拥有世界先进的第三代电磁导航手术系统、等离子消融等设备，微创骨科是其特色。医院肝移植技术获得广东省医学会授予A级资格，肾移植技术获卫生部核准开展的项目，已成功开展卵巢移植、肾移植、肝移植、骨髓移植、角膜移植、心脏移植、肺移植等先进的高难度手术。医院一直致力于特色专科的建设。产科学、妇科学、骨外科学、生殖医学是医院的重点学科，新生儿科、心血管内科、器官移植科是医院的重点专科。
     * headimgurl : null
     */
    private String distance;
    private String address;
    private String hospitaId;
    private String name;
    private String introduce;
    private String pictureUrl;

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHospitaId(String hospitaId) {
        this.hospitaId = hospitaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setHeadimgurl(String headimgurl) {
        this.pictureUrl = headimgurl;
    }

    public String getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public String getHospitaId() {
        return hospitaId;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getHeadimgurl() {
        return pictureUrl;
    }
}
