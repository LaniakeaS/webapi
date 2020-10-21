public class IDGenerator {

    private final long epoch = 1491004800000L;
    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;
    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDataCenterId = ~(-1 << dataCenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    private final long timestampShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);
    private long dataCenterId;
    private long workerId;
    private long sequence;
    private long lastTimestamp = -1L;

    public IDGenerator(long dataCenterId, long workerId) {

        if (dataCenterId > maxDataCenterId || dataCenterId < 0)
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0",
                    maxDataCenterId));

        if (workerId > maxWorkerId || workerId < 0)
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));

        this.dataCenterId = dataCenterId;
        this.workerId = workerId;

    }


    public synchronized long nextId() {

        long timestamp = timeGen();

        if (timestamp < lastTimestamp)
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));

        if (timestamp == lastTimestamp) {

            sequence = (sequence + 1) & sequenceMask;

            if (sequence == 0)
                timestamp = nextMillis(lastTimestamp);

        } else
            sequence = 0L;

        lastTimestamp = timestamp;
        return ((timestamp - epoch) << timestampShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;

    }

    protected long timeGen() {

        return System.currentTimeMillis();

    }

    protected long nextMillis(long lastTimestamp) {

        long timestamp = timeGen();

        while (timestamp <= lastTimestamp)
            timestamp = lastTimestamp;

        return timestamp;

    }
}