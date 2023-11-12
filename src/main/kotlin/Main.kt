import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import org.litote.kmongo.eq
import org.litote.kmongo.serialization.SerializationClassMappingTypeService
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.litote.kmongo.withKMongo
import java.util.UUID

class BanResetsArgs(parser: ArgParser)
{
    val connectionUri by parser.storing("mongo connection uri")
        .default("mongodb://localhost")

    val banResetReason by parser.storing("removal reason for ban")
        .default("Manual ban reset")

    val database by parser.storing("mongo database name")
    val collection by parser.storing("mongo collection name")
        .default("Punishment")
}

data class Punishment(
    val category: String,

    val removedReason: String?,
    val removedOn: String?,
    val removedAt: String?
)

fun main(args: Array<String>) = mainBody {
    System.setProperty(
        "org.litote.mongo.mapping.service",
        SerializationClassMappingTypeService::class.qualifiedName!!
    )

    with(ArgParser(args).parseInto(::BanResetsArgs)) {
        val client = MongoClients.create(connectionUri)
        val database = client.getDatabase(database).withKMongo()
        val collection = database.getCollection(collection).withKMongo()

        with(
            collection.updateMany(
                Punishment::category eq "BAN",
                set(
                    Punishment::removedReason setTo banResetReason,
                    Punishment::removedOn setTo "app:banreset",
                    Punishment::removedAt setTo "${System.currentTimeMillis()}"
                )
            )
        ) {
            if (wasAcknowledged())
            {
                println("Completed modification of $modifiedCount documents.")
            } else
            {
                println("Failed to complete.")
            }
        }
    }
}
