import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import org.litote.kmongo.withKMongo

class BanResetsArgs(parser: ArgParser)
{
    val connectionUri by parser.storing("mongo connection uri")
        .default("mongodb://localhost")

    val database by parser.storing("mongo database name")
    val collection by parser.storing("mongo collection name")
        .default("Punishment")
}

fun main(args: Array<String>) = mainBody {
    with(ArgParser(args).parseInto(::BanResetsArgs)) {
        val client = MongoClients.create(connectionUri)
        val database = client.getDatabase(database).withKMongo()
        val collection = database.getCollection(collection).withKMongo()

        with(
            collection.deleteMany(
                Filters.eq("category", "BAN")
            )
        ) {
            if (wasAcknowledged())
            {
                println("Completed deletion of $deletedCount documents.")
            } else
            {
                println("Failed to complete.")
            }
        }
    }
}
