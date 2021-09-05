package geekbrains.ru.translator.model.repository

import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.model.datasource.DataSource
import com.uzlov.translator.model.repository.Repository
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<WordModel>>) :
    Repository<List<WordModel>> {

    override fun getData(word: String): Observable<List<WordModel>> {
        return dataSource.getData(word)
    }
}
