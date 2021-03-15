# TTCalendar

# 1. Using TTCalendar

## Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
## Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.TOMTOMSOFT:TTCalendar:0.9.5'
	}

# 2. Sample xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto"
	xmlns:tools="http://schemas.android.com/tools"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:background="#B2E3C3"
	tools:context=".MainActivity">


	<com.tomtomsoft.ttcalendar.TTCalView
		android:id="@+id/calView"
		android:layout_width="360dp"
		android:layout_height="400dp"
		android:background="#a6a6a6"

		app:ttcal_header="true"
		app:ttcal_textfont="@font/nanum_square_eb"
		app:ttcal_headerbg="#EAAECD"

		app:layout_constraintTop_toTopOf="parent"
		app:layout_constraintLeft_toLeftOf="parent"
		app:layout_constraintRight_toRightOf="parent"
		app:layout_constraintBottom_toBottomOf="parent"
		app:layout_constraintVertical_bias="0.2"

		/>

</androidx.constraintlayout.widget.ConstraintLayout>
```


# 3. In Activity

```
public class MainActivity extends AppCompatActivity {
	private String TAG = MainActivity.class.getSimpleName();
	TTCalView calView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calView = findViewById(R.id.calView);
		calView.showHeader(true);
		calView.selectDate(2021, 3, 14, true);
		calView.listener = new TTCalView.TTCalViewX() {
			@Override
			public void onDaySelected(int y, int m, int d, int state) {

				String sTemp = String.format("day selected: y=%d,m=%d,d=%d, state=%d", 
								y, m, d, state);
				Log.d(TAG, sTemp);
			}

			@Override
			public void onPrevMonth() {

				calView.movePrevMonth();
			}

			@Override
			public void onNextMonth() {
				calView.moveNextMonth();
			}
		};

	}
}
```
# 4. Additional access method


## 4.1. Set Date 
	
	public void setDate(int y, int m, int d)

  
## 4.2. Get Every cell - total 42 (7day x 6week) cells
    
	public TTDateCell[] getDays()



You can retrieve all days (7*6 cells).

	TTDateCell cells[] = calView.getDays();

	// date loop
	TTCalView.TTDateCell cells[] = calView.getDays();
	for(int i=0; i<cells.length; i++) {

		String sTemp = String.format("cell[%d]: y=%d,m=%d,d=%d, state=%d",
								i, cells[i].y, cells[i].m, cells[i].m);
		Log.d(TAG, sTemp);
	}

## 4.3. Get Header area
    
	LinearLayout llHeaderArea = calView.getHeaderArea();

With this, You can change header area background color and so on.

## 4.4. Get Week title rea.
    
	LinearLayout llWeekTitleArea = calView.getWeekArea();

    // Get Title TextView
    TextView tvTitle = calView.getHeaderTitle();


## 4.5. Retrieve Year, Month, Day
	
	public int getYear()  // ex: returns 2021

	public int getMonth() // ex: returns 3

	public int getDay()   // ex: 31


## 4.6 TTDateCell class


	public class TTDateCell {

		int nState = 0;

		public int y;
		public int m;
		public int d;
		public int state = 0;

		public LinearLayout llCell = null;

		public TTDateCell(int y, int m, int d, int state, LinearLayout cell) {
			this.y = y;
			this.m = m;
			this.d = d;
			this.llCell = cell;
		}
		public void reset() {
			y = m = d = state = 0;
			//llCell = null;  // !!!!
		}
		public void setData(int y, int m, int d, int state) {
			this.y = y;
			this.m = m;
			this.d = d;
			this.state = state;
		}

	}	


# 5. More

For more, please look into MainActivity.java in sample.

# 6. Screenshots

![gtkcalendar-1](https://user-images.githubusercontent.com/55382461/111071418-79425900-8519-11eb-9c3c-393100afb5ad.png)
![gtkcalendar-2](https://user-images.githubusercontent.com/55382461/111071420-7ba4b300-8519-11eb-9ec8-4a0bf5d3ece2.png)
![gtkcalendar-3](https://user-images.githubusercontent.com/55382461/111071427-7f383a00-8519-11eb-9541-199a87debd9d.png)


