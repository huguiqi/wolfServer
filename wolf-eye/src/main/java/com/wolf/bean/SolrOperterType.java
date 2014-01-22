package com.wolf.bean;


public enum SolrOperterType {

    ADD{
        @Override
            public String converToJsonString(
                    DemoSolrBook demoSolrBook) {
                StringBuilder builder=new StringBuilder("{");
                
                builder.append("\"add\": {\"doc\":" + demoSolrBook.toJsonString()+ "} ");
                String options = ",\"commit\": {}";
                builder.append( options + "}");
                return builder.toString();
            }
    };
    public abstract String converToJsonString(DemoSolrBook demoSolrBook);
}
