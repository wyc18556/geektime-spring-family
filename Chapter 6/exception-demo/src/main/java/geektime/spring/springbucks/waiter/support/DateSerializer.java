package geektime.spring.springbucks.waiter.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * @author wyc1856
 * @date 2019/11/11
 */
@JsonComponent
public class DateSerializer extends StdSerializer<Temporal> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public DateSerializer() {
        super(Temporal.class);
    }

    @Override
    public void serialize(Temporal temporal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (temporal instanceof LocalDateTime) {
            jsonGenerator.writeString(DATE_TIME_FORMATTER.format(temporal));
        }else if (temporal instanceof LocalDate){
            jsonGenerator.writeString(DATE_FORMATTER.format(temporal));
        }else if (temporal instanceof LocalTime){
            jsonGenerator.writeString(TIME_FORMATTER.format(temporal));
        }
    }
}
