package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Server {

    @SerializedName("ok")
    @Expose
    private Boolean ok;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("elapsed")
    @Expose
    private String elapsed;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

	@Override
	public String toString() {
		return "Server [ok=" + ok + ", stats=" + stats + ", elapsed=" + elapsed + "]";
	}

}
