package alvarez.wilfredo.dynamodb.services;

import alvarez.wilfredo.dynamodb.mappers.DynamoDBMapperImpl;
import alvarez.wilfredo.dynamodb.models.User;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class UsersService {
    private static final String TABLE_NAME = "users";
    private static final String REGION = "eu-north-1";
    public void guardar(User user) {
        try {
            DynamoDBMapperImpl.getClient(REGION).save(user,
                    DynamoDBMapperImpl.buildMapperConfig(TABLE_NAME));
        } catch (DynamoDBMappingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<User> obtenerPorId(UUID id) {
        User partitionKey = new User();
        partitionKey.setId(id);
        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withHashKeyValues(partitionKey);

        return DynamoDBMapperImpl.getClient(REGION).query(User.class, queryExpression);
    }
}
