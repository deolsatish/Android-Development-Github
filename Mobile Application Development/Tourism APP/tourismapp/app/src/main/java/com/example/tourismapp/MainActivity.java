package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements com.example.realestateapp.PropertyAdapter.OnRowClickListener {

    RecyclerView horizontalRecyclerView;
    HorizontalAdapter horizontalAdapter;

    RecyclerView propertyRecyclerView;
    com.example.realestateapp.PropertyAdapter propertyAdapter;

    List<touristplace> placeslist= new ArrayList<>();
    Integer[] imageList = {R.drawable.ngv, R.drawable.rbg, R.drawable.zoo, R.drawable.museum, R.drawable.beach};
    String[] titleList = {"National Gallery of Victoria", "Royal Botanic Gardens Victoria","Melbourne Zoo","Melbourne Museum","St Kilda beach"};
    String[] addressList = {"180 St Kilda Rd, Melbourne VIC 3006", "Melbourne VIC 3004","Elliott Ave, Parkville VIC 3052","11 Nicholson St, Carlton VIC 3053","St Kilda Foreshore, St Kilda, Victoria, 3182"};
    String[] ppaList = {"1.5+ million (approx. per year)", "2+ million (approx. per year)","2.5 million people per annum","1.19 million visitors per annum","2.0 million visitors per annum"};
    String[] descriptionList = {"National Gallery of Victoria is the oldest public art museum in Australia. It is divided into 2 sections- The Ian Potter Centre and the NGV International. There are over 68,000 works of art here, some of them being Acrobats by Albert Gleizes, Airport at Night by Russell Drysdale, Combat by Lee Krasner, Bamboo in Spring Rain by WU Zhen and Clouds by John Constable.\n" +
            "\n" +
            "Attend an introductory talk, opt for a guided tour or watch a documentary film at the gallery to learn all about the exhibitions here.\n" +
            "\n" +
            "NGV International - Better known as the NGV, it houses the Gallery’s international collection and has some exclusive pieces from Europe, Asia, America and Oceania.\n" +
            "\n" +
            "Ian Potter Centre - A spectacular showcase comprising more than 20 galleries, The Ian Potter Centre: NGV Australia Art Gallery is the world’s first major gallery exclusively dedicated to Australian art.", "A must-visit for those in love with nature, the Royal Botanic Gardens is one of the most important attractions in Melbourne. The Royal Botanic Gardens were set up in the year 1846, and comprise 38 hectares of land dedicated to plant science research, and is quite a sight to witness.\n" +
            "\n" +
            "With 10,000 different species of flora divided into various areas like Herb Garden, Rose Garden, Arid Garden, Fern Gully, Bulbs and several lawns, Royal Botanic Gardens finds its place among the most popular botanical gardens in the world. It also hosts several events throughout the year, which attract several tourists as well as locals to Melbourne.\n" +
            "\n" +
            "Events range from horticulture exhibitions and festivals to tours, picnics and even weddings!",
            "Are you an animal lover? There couldn’t be a better place for you! Head to the Melbourne Zoo for unlimited moments of fun with your furry friends. Enjoy close encounters with endangered Sumatran Tigers, spot exquisite marine creatures, watch the little penguins play and get to know interesting tit bits about some of the rarest species of animals.\n" +
                    "\n" +
                    "Butterfly House, Trail of the Elephants, Orang-utan Sanctuary, Baboon Lookout and the Zoological gardens are other highlights of the Melbourne Zoo.",
            "Explore the rich history of Melbourne as you witness the many exhibits and interactive displays at the Melbourne Museum. The museum has an entire gallery called Bunjilaka dedicated to Aboriginal art from South Eastern Australia, which is worth a visit.\n" +
                    "\n" +
                    "The IMAX theatre is another major attraction that is housed within the museum building. Some short films that are featured here are Born to be Wild 3D, Hidden Universe 3D, Gravity 3D and Forces of Nature, to name a few. Separate tickets need to be purchased for these.",
    "St Kilda is mostly associated with beach side nightlife – perfect for a romantic evening! Just book a table by the beach and enjoy the waves crashing against the sand as you sit and enjoy a romantic dinner and evening. What’s more, the sandy beach is also an excellent place for swimming, biking, rollerblading and picnics.\n" +
            "\n" +
            "Some of the places you can check out at St Kilda include St. Kilda Esplanade where you can go on long walks or skating or sunbathing. St Kilda Pier is also very popular for the stunning city skyline and sunset views it offers."};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        horizontalRecyclerView=findViewById(R.id.horizontalRecyclerView);
        horizontalAdapter=new HorizontalAdapter(placeslist,MainActivity.this);
        horizontalRecyclerView.setAdapter(horizontalAdapter);
        RecyclerView.LayoutManager horizontallayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        horizontalRecyclerView.setLayoutManager(horizontallayoutManager);


        propertyRecyclerView = findViewById(R.id.verticalRecyclerView);
        propertyAdapter = new com.example.realestateapp.PropertyAdapter(placeslist , MainActivity.this, (com.example.realestateapp.PropertyAdapter.OnRowClickListener) this);
        propertyRecyclerView.setAdapter(propertyAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        propertyRecyclerView.setLayoutManager(layoutManager);



        for (int i= 0 ; i< imageList.length; i++)
        {

            com.example.tourismapp.touristplace place = new com.example.tourismapp.touristplace(i, imageList[i],titleList[i],ppaList[i],descriptionList[i], addressList[i]);
            placeslist.add(place);

        }

    }
    @Override
    public void onItemClick(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment=new ngvFragment();
                break;
            case 1:
                fragment=new rgbFragment();
                break;
            case 2:
                fragment=new zooFragment();
                break;
            case 3:
                fragment=new museumFragment();
                break;
            case 4:
                fragment= new BreachFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,fragment).addToBackStack(null).commit();

    }
}