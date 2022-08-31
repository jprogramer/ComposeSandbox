package com.example.sandbox.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sandbox.data.Entry
import com.example.sandbox.data.SandboxDatabase
import com.example.sandbox.util.launchOnIO
import com.example.sandbox.util.pagingItems

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // write few rows to the database manually just to test
        launchOnIO {
            SandboxDatabase.INSTANCE.entryDAO().replaceAll(Entry.fakeData("manual"))
        }

        setContent {
            MaterialTheme {
                val viewModel: EntriesViewModel = viewModel()
                val entries = viewModel.getEntriesItemsData().collectAsLazyPagingItems()

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            Log.i("£££", "refresh button clicked")
                            entries.refresh()
                        }) { Text("refresh") }
                    },
                    content = { EntriesList(entries) },
                )
            }
        }
    }

    @Composable
    private fun EntriesList(entries: LazyPagingItems<Entry>) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
        ) {
            pagingItems(entries, Entry::id) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(">>>")
                    Text(it?.value.toString())
                    Text("<<<")
                }
            }
        }
    }
}