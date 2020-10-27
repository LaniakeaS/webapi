/**
 * <h3>Description:</h3>
 * Use snowflake algorithm from Twitter to generate ID.<br>
 * <br>
 * @author Twitter<br>
 */
public class IDGenerator {

    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;
    private final long dataCenterId;
    private final long workerId;
    private long sequence;
    private long lastTimestamp = -1L;

    public IDGenerator(long dataCenterId, long workerId) {

        long maxDataCenterId = ~(-1 << dataCenterIdBits);
        if (dataCenterId > maxDataCenterId || dataCenterId < 0)
            throw new IllegalArgumentException(String.format("dataCenterId can't be greater than %d or less than 0",
                    maxDataCenterId));

        long maxWorkerId = ~(-1L << workerIdBits);
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

        long sequenceBits = 12L;
        if (timestamp == lastTimestamp) {

            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;

            if (sequence == 0)
                timestamp = nextMillis(lastTimestamp);

        } else
            sequence = 0L;

        lastTimestamp = timestamp;
        long epoch = 1491004800000L;
        long dataCenterIdShift = sequenceBits + workerIdBits;
        long timestampShift = sequenceBits + workerIdBits + dataCenterIdBits;
        return ((timestamp - epoch) << timestampShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << sequenceBits) |
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