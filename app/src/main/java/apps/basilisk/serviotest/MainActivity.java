package apps.basilisk.serviotest;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import apps.basilisk.serviotest.model.Place;
import apps.basilisk.serviotest.model.PlaceGroup;
import apps.basilisk.serviotest.model.PlaceGroupSchema;
import apps.basilisk.serviotest.model.PlaceUnion;
import apps.basilisk.serviotest.model.Places;


public class MainActivity extends AppCompatActivity implements Observer {

    private static final String DATA_LIST = "DATA_LIST";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<HashMap<String, Object>> dataList;
    private DataLoader dataLoader;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.refresh_list);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataLoader.loadPlaces();
            }
        });

        dataList = new ArrayList<>();

        buildRecyclerView();

        dataLoader = DataLoader.getInstance();
        dataLoader.addObserver(this);

        if (dataList.size() == 0 && savedInstanceState == null) {
            swipeRefreshLayout.setRefreshing(true);
            dataLoader.loadPlaces();
        } else {
            dataList.clear();
            dataList.addAll((ArrayList<HashMap<String, Object>>) savedInstanceState.getSerializable(DATA_LIST));
            placeAdapter.notifyDataSetChanged();
        }

    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(dataList);
        placeAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Place place = (Place) dataList.get(position).get("placeObject");
                if (place != null)
                    Toast.makeText(getApplicationContext(), place.toString(), Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setAdapter(placeAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = (int) viewHolder.itemView.getTag();
                if (dataList.get(position) != null) {
                    dataList.remove(position);
                    placeAdapter.notifyDataSetChanged();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoader.deleteObserver(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(DATA_LIST, dataList);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Pair) {
            String keyName = (String) ((Pair) arg).first;
            switch (keyName) {
                case "ERROR_PLACES":
                    String message = (String) ((Pair) arg).second;
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;

                case "RESULT_PLACES":
                    HashMap<String, Object> hashMap;
                    String prefix;

                    Places objectPlaces = (Places) ((Pair) arg).second;
                    List<PlaceUnion> unions = objectPlaces.getPlaceUnions();
                    if (unions != null && unions.size() > 0) {
                        dataList.clear();
                        for (PlaceUnion union : unions) {
                            List<PlaceGroup> groups = union.getPlaceGroups();
                            if (groups != null && groups.size() > 0) {
                                for (PlaceGroup group : groups) {
                                    List<PlaceGroupSchema> schemas = group.getPlaceGroupSchemas();
                                    if (schemas != null && schemas.size() > 0) {
                                        for (PlaceGroupSchema schema : schemas) {
                                            List<Place> places = schema.getPlaces();
                                            if (places != null && places.size() > 0) {
                                                for (Place place : places) {
                                                    prefix = union.getName() + ", " + group.getName() + ", " + schema.getName();
                                                    hashMap = new HashMap<>();
                                                    hashMap.put("prefix", prefix);
                                                    hashMap.put("placeObject", place);
                                                    dataList.add(hashMap);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        placeAdapter.notifyDataSetChanged();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    }
}
