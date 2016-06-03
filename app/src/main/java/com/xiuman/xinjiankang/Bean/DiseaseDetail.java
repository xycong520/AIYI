package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：疾病详情
 * Created by hxy on 2015/8/31.
 */
public class DiseaseDetail implements Serializable {

    private String success;
    private String message;
    private DiseaseDetailEnpty datasource;
    private String totaldatasource;
    private int page;
    private int totalpage;

    public static class DiseaseDetailEnpty implements Serializable{

        private String id;
        //病因
        private String cause;
        //临床表现
        private String manifestations;
        //别名
        private String alias;
        //治疗
        private String treatment;
        //名称
        private String name;
        //预防
        private String prevention;
        private String instructions;
        private String casesCategory;
        private String checkDiagnosis;
        private List<casesList> casesList;
        public static class casesList implements Serializable{
            private String content;
            private String casesId;
            private String sex;
            private int age;
            private double createDate;

            public String getContent() {
                return content;
            }
            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
            public void setContent(String content) {
                this.content = content;
            }

            public String getCasesId() {
                return casesId;
            }

            public void setCasesId(String casesId) {
                this.casesId = casesId;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public double getCreateDate() {
                return createDate;
            }

            public void setCreateDate(double createDate) {
                this.createDate = createDate;
            }
        }

        public List<DiseaseDetailEnpty.casesList> getCasesList() {
            return casesList;
        }

        public void setCasesList(List<DiseaseDetailEnpty.casesList> casesList) {
            this.casesList = casesList;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getManifestations() {
            return manifestations;
        }

        public void setManifestations(String manifestations) {
            this.manifestations = manifestations;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTreatment() {
            return treatment;
        }

        public void setTreatment(String treatment) {
            this.treatment = treatment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrevention() {
            return prevention;
        }

        public void setPrevention(String prevention) {
            this.prevention = prevention;
        }

        public String getCasesCategory() {
            return casesCategory;
        }

        public void setCasesCategory(String casesCategory) {
            this.casesCategory = casesCategory;
        }

        public String getCheckDiagnosis() {
            return checkDiagnosis;
        }

        public void setCheckDiagnosis(String checkDiagnosis) {
            this.checkDiagnosis = checkDiagnosis;
        }
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DiseaseDetailEnpty getDatasource() {
        return datasource;
    }

    public void setDatasource(DiseaseDetailEnpty datasource) {
        this.datasource = datasource;
    }

    public String getTotaldatasource() {
        return totaldatasource;
    }

    public void setTotaldatasource(String totaldatasource) {
        this.totaldatasource = totaldatasource;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

}
