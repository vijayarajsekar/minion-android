package com.tomclaw.minion;

import com.tomclaw.minion.storage.MemoryStorage;
import com.tomclaw.minion.storage.Readable;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by solkin on 01.08.17.
 */
public class MemoryStorageUnitTest {

    @Test
    public void readAfterWrite_dataEquals() throws Exception {
        byte[] writeData = createRandomData();
        MemoryStorage storage = createMemoryStorage();
        OutputStream output = storage.write();

        output.write(writeData);
        output.close();

        byte[] readData = readFully(storage);

        assertArrayEquals(writeData, readData);
    }

    private byte[] createRandomData() {
        return "sample data".getBytes();
    }

    private MemoryStorage createMemoryStorage() {
        return MemoryStorage.create();
    }

    private byte[] readFully(Readable readable) throws IOException {
        ByteArrayOutputStream readDataStream = new ByteArrayOutputStream();
        InputStream input = readable.read();
        int read;
        while ((read = input.read()) != -1) {
            readDataStream.write(read);
        }
        byte[] readData = readDataStream.toByteArray();
        readDataStream.close();
        input.close();
        return readData;
    }
}
