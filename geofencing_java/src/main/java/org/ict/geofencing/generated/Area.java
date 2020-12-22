package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("elapsed")
    @Expose
    private String elapsed;
    @SerializedName("err")
    @Expose
    private String erro;

    
    public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

	@Override
	public String toString() {
		return "TestObject [ok=" + ok + ", result=" + result + ", elapsed=" + elapsed + "]";
	}

}
