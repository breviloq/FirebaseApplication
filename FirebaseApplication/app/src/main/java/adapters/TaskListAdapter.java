package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebaseapplication.R;

import java.util.ArrayList;

import models.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

    public TaskListAdapter(@NonNull Context context, @NonNull ArrayList<Task> objects) {
        super(context, R.layout.task_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task currentTask = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.task_item, null, false);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvIsMandatory = v.findViewById(R.id.tvIsMandatory);
        TextView tvCount = v.findViewById(R.id.tvCount);

        tvTitle.setText(currentTask.getTitle());
        tvCount.setText("Questions: " + currentTask.getQuestionsCount());
        tvIsMandatory.setText( currentTask.isMandatory() ? "Mandatory" : "Not mandatory" );

        return v;
    }
}
