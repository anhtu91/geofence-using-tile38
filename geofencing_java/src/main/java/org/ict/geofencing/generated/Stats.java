
package org.ict.geofencing.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("aof_size")
    @Expose
    private Integer aofSize;
    @SerializedName("avg_item_size")
    @Expose
    private Integer avgItemSize;
    @SerializedName("cpus")
    @Expose
    private Integer cpus;
    @SerializedName("heap_released")
    @Expose
    private Integer heapReleased;
    @SerializedName("heap_size")
    @Expose
    private Integer heapSize;
    @SerializedName("http_transport")
    @Expose
    private Boolean httpTransport;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("in_memory_size")
    @Expose
    private Integer inMemorySize;
    @SerializedName("max_heap_size")
    @Expose
    private Integer maxHeapSize;
    @SerializedName("mem_alloc")
    @Expose
    private Integer memAlloc;
    @SerializedName("num_collections")
    @Expose
    private Integer numCollections;
    @SerializedName("num_hooks")
    @Expose
    private Integer numHooks;
    @SerializedName("num_objects")
    @Expose
    private Integer numObjects;
    @SerializedName("num_points")
    @Expose
    private Integer numPoints;
    @SerializedName("num_strings")
    @Expose
    private Integer numStrings;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("pointer_size")
    @Expose
    private Integer pointerSize;
    @SerializedName("read_only")
    @Expose
    private Boolean readOnly;
    @SerializedName("threads")
    @Expose
    private Integer threads;
    @SerializedName("version")
    @Expose
    private String version;

    public Integer getAofSize() {
        return aofSize;
    }

    public void setAofSize(Integer aofSize) {
        this.aofSize = aofSize;
    }

    public Integer getAvgItemSize() {
        return avgItemSize;
    }

    public void setAvgItemSize(Integer avgItemSize) {
        this.avgItemSize = avgItemSize;
    }

    public Integer getCpus() {
        return cpus;
    }

    public void setCpus(Integer cpus) {
        this.cpus = cpus;
    }

    public Integer getHeapReleased() {
        return heapReleased;
    }

    public void setHeapReleased(Integer heapReleased) {
        this.heapReleased = heapReleased;
    }

    public Integer getHeapSize() {
        return heapSize;
    }

    public void setHeapSize(Integer heapSize) {
        this.heapSize = heapSize;
    }

    public Boolean getHttpTransport() {
        return httpTransport;
    }

    public void setHttpTransport(Boolean httpTransport) {
        this.httpTransport = httpTransport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInMemorySize() {
        return inMemorySize;
    }

    public void setInMemorySize(Integer inMemorySize) {
        this.inMemorySize = inMemorySize;
    }

    public Integer getMaxHeapSize() {
        return maxHeapSize;
    }

    public void setMaxHeapSize(Integer maxHeapSize) {
        this.maxHeapSize = maxHeapSize;
    }

    public Integer getMemAlloc() {
        return memAlloc;
    }

    public void setMemAlloc(Integer memAlloc) {
        this.memAlloc = memAlloc;
    }

    public Integer getNumCollections() {
        return numCollections;
    }

    public void setNumCollections(Integer numCollections) {
        this.numCollections = numCollections;
    }

    public Integer getNumHooks() {
        return numHooks;
    }

    public void setNumHooks(Integer numHooks) {
        this.numHooks = numHooks;
    }

    public Integer getNumObjects() {
        return numObjects;
    }

    public void setNumObjects(Integer numObjects) {
        this.numObjects = numObjects;
    }

    public Integer getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(Integer numPoints) {
        this.numPoints = numPoints;
    }

    public Integer getNumStrings() {
        return numStrings;
    }

    public void setNumStrings(Integer numStrings) {
        this.numStrings = numStrings;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPointerSize() {
        return pointerSize;
    }

    public void setPointerSize(Integer pointerSize) {
        this.pointerSize = pointerSize;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	@Override
	public String toString() {
		return "Stats [aofSize=" + aofSize + ", avgItemSize=" + avgItemSize + ", cpus=" + cpus + ", heapReleased="
				+ heapReleased + ", heapSize=" + heapSize + ", httpTransport=" + httpTransport + ", id=" + id
				+ ", inMemorySize=" + inMemorySize + ", maxHeapSize=" + maxHeapSize + ", memAlloc=" + memAlloc
				+ ", numCollections=" + numCollections + ", numHooks=" + numHooks + ", numObjects=" + numObjects
				+ ", numPoints=" + numPoints + ", numStrings=" + numStrings + ", pid=" + pid + ", pointerSize="
				+ pointerSize + ", readOnly=" + readOnly + ", threads=" + threads + ", version=" + version + "]";
	}

}
