Override public void setUp() throws Exception {
    super.setUp();
    username=TestConstants.USERNAME;
    password=TestConstants.PASSWORD;
    serverUrl=TestConstants.SERVER_URL;
    wikiName=TestConstants.WIKI_NAME;
    spaceName=TestConstants.SPACE_NAME;
    pageName=TestConstants.CREATE_PAGE_NAME + "-" + count;
    attachmentName=TestConstants.ATTACHMENT_NAME;
    objClsName1=TestConstants.OBJECT_CLASS_NAME_1;
    objClsName2=TestConstants.OBJECT_CLASS_NAME_2;
    rm=new XmlRESTFulManager(serverUrl,username,password);
    api=rm.getRestConnector();
    rao=rm.newDocumentRao();
    doc=new Document(wikiName,spaceName,pageName);
    doc.setTitle(pageName);
    api.deletePage(wikiName,spaceName,pageName);
    if (count == 9) {
      Application sys=XWikiApplicationContext.getInstance();
      FileOutputStream fos=sys.openFileOutput(attachmentName,Context.MODE_WORLD_READABLE);
      PrintWriter writer=new PrintWriter(fos);
      writer.println("this is a text attachment.");
      writer.flush();
      writer.close();
      FileInputStream fis=sys.openFileInput(attachmentName);
      byte[] buff=new byte[30];
      int i=0;
      while (i != -1) {
        i=fis.read(buff);
      }
      Log.d(TAG,new String(buff));
      af1=sys.getFileStreamPath(attachmentName);
    }
    Log.d(TAG,"setup test method:" + count);
  }

  public String getApplicationVersionName(){
    if (uiActivity != null) {
      Application app=(uiActivity).getApplication();
      String name=app.getPackageName();
      PackageInfo pi;
      try {
        pi=app.getPackageManager().getPackageInfo(name,0);
        return pi.versionName;
      }
   catch (    NameNotFoundException e) {
      }
    }
    return "";
  }
  public static MoteContext getInstance(Activity activity){
    Application application=activity.getApplication();
    if (!(application instanceof MoteApplication)) {
      throw new RuntimeException("Application singleton must implement MoteApplication.");
    }
    MoteApplication moteApplication=(MoteApplication)application;
    return moteApplication.getMoteContext();
  }
  super.onCreate(savedInstanceState);
  Application app=getApplication();
  if (app instanceof BeemApplication) {
    mBeemApplication=(BeemApplication)app;
    if (mBeemApplication.isConnected()) {
      startActivity(new Intent(this,ContactList.class));
      finish();
    }
 else     if (!mBeemApplication.isAccountConfigured()) {
      startActivity(new Intent(this,Account.class));
      finish();
    }
  }
  setContentView(R.layout.login);
  mTextView=(TextView)findViewById(R.id.log_as_msg);
}
rivate void registerIntentReceivers(){
    if (sWallpaperReceiver == null) {
      final Application application=getApplication();
      sWallpaperReceiver=new WallpaperIntentReceiver(application,this);
      IntentFilter filter=new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED);
      application.registerReceiver(sWallpaperReceiver,filter);
    }
   else {
      sWallpaperReceiver.setLauncher(this);
    }
    IntentFilter filter=new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
    filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
    filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
    filter.addDataScheme("package");
    registerReceiver(mApplicationsReceiver,filter);
  }
  private void registerIntentReceivers(){
    boolean useNotifReceiver=AlmostNexusSettingsHelper.getNotifReceiver(this);
    if (useNotifReceiver && mCounterReceiver == null) {
      mCounterReceiver=new CounterReceiver(this);
      mCounterReceiver.setCounterListener(new CounterReceiver.OnCounterChangedListener(){
        public void onTrigger(      String pname,      int counter,      int color){
          updateCountersForPackage(pname,counter,color);
        }
      }
  );
      registerReceiver(mCounterReceiver,mCounterReceiver.getFilter());
    }
    if (sWallpaperReceiver == null) {
      final Application application=getApplication();
      sWallpaperReceiver=new WallpaperIntentReceiver(application,this);
      IntentFilter filter=new IntentFilter(Intent.ACTION_WALLPAPER_CHANGED);
      application.registerReceiver(sWallpaperReceiver,filter);
    }
   else {
      sWallpaperReceiver.setLauncher(this);
    }
    IntentFilter filter=new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
    filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
    filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
    filter.addDataScheme("package");
    registerReceiver(mApplicationsReceiver,filter);
    filter=new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    registerReceiver(mCloseSystemDialogsReceiver,filter);
    filter=new IntentFilter();
    filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
    filter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
    registerReceiver(mApplicationsReceiver,filter);
  
    public static MobeelizerApplication createApplication(final Application mobeelizer){
        MobeelizerApplication application=new MobeelizerApplication();
        Bundle metaData=application.getMetaData(mobeelizer);
        String device=metaData.getString(META_DEVICE);
        String entityPackage=metaData.getString(META_PACKAGE);
        String definitionXml=metaData.getString(META_DEFINITION_ASSET);
        String developmentRole=metaData.getString(META_DEVELOPMENT_ROLE);
        int databaseVersion=metaData.getInt(META_DATABASE_VERSION,1);
        String url=metaData.getString(META_URL);
        String stringMode=metaData.getString(META_MODE);
        if (entityPackage == null) {
          throw new IllegalStateException(META_PACKAGE + " must be set in manifest file.");
        }
        if (definitionXml == null) {
          definitionXml="application.xml";
        }
        application.initApplication(mobeelizer,device,entityPackage,developmentRole,definitionXml,databaseVersion,url,stringMode);
        return application;
      }
      static ApplicationsState getInstance(Application app){
        synchronized (sLock) {
            if (sInstance == null) {
              sInstance=new ApplicationsState(app);
            }
            return sInstance;
          }
        }
        public static void initialize(Application application){
            CONTEXT=application;
            int version=getPrefs().getInt(KEY_PREFS_VERSION,VER_LEGACY);
          switch (version) {
          case VER_LEGACY:
              if (LOGV)     Log.v(TAG,"Upgrading settings from version " + version + " to version "+ VER_LAUNCH);
            version=VER_LAUNCH;
          }
          PrefsUtils.apply(getPrefs().edit().putInt(KEY_PREFS_VERSION,version));
          }
          static ApplicationsState getInstance(Application app){
            synchronized (sLock) {
                if (sInstance == null) {
                  sInstance=new ApplicationsState(app);
                }
                return sInstance;
              }
            }
            public static GoogleAnalyticsSessionHelper getInstance(String key,Application appContext){
                if (INSTANCE == null) {
                  INSTANCE=new GoogleAnalyticsSessionHelper(key,appContext);
                }
                return INSTANCE;
              }
              final String text=Reader.getTextView().getSelectedText();
              Reader.getTextView().clearSelection();
              final ClipboardManager clipboard=(ClipboardManager)BaseActivity.getApplication().getSystemService(Application.CLIPBOARD_SERVICE);
              clipboard.setText(text);
              UIUtil.showMessageText(BaseActivity,ZLResource.resource("selection").getResource("textInBuffer").getValue().replace("%s",clipboard.getText()));
            }
            final String text=Reader.getTextView().getSelectedText();
            Reader.getTextView().clearSelection();
            final ClipboardManager clipboard=(ClipboardManager)BaseActivity.getApplication().getSystemService(Application.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            UIUtil.showMessageText(BaseActivity,ZLResource.resource("selection").getResource("textInBuffer").getValue().replace("%s",clipboard.getText()));
          }          final String text=Reader.getTextView().getSelectedText();
          Reader.getTextView().clearSelection();
          final ClipboardManager clipboard=(ClipboardManager)BaseActivity.getApplication().getSystemService(Application.CLIPBOARD_SERVICE);
          clipboard.setText(text);
          UIUtil.showMessageText(BaseActivity,ZLResource.resource("selection").getResource("textInBuffer").getValue().replace("%s",clipboard.getText()));
        }
        public static Tracker instance(Application application){
            if (tracker == null) {
              tracker=new Tracker(application);
            }
            tracker.begin();
            return tracker;
          }
          @Inject public OakHttpTool(Application application){
            mApplication=application;
            installCache();
            try {
              appVersionName=mApplication.getPackageManager().getPackageInfo(mApplication.getPackageName(),0).versionName;
              appVersionCode=mApplication.getPackageManager().getPackageInfo(mApplication.getPackageName(),0).versionCode;
            }
           catch (  PackageManager.NameNotFoundException e) {
              Log.e(TAG,"There was an error setting the version for the app");
            }
          }
          public void setupObject(boolean restore){
            PackageManager pm=getPackageManager();
            ApplicationInfo ai=getApplicationInfo();
            String name=(String)pm.getApplicationLabel(ai);
            if (restore) {
              try {
                GlobalConfig config=GlobalConfig.getInstance();
                byte[] byte_in=null;
                SystemState.Application state;
                CCNHandle handle=config.getCCNHandle();
                byte_in=config.loadConfig("applications",name);
                state=SystemState.Application.parseFrom(byte_in);
                this._pull_rate=state.getPullRate();
                setupObject();
                for (      String stream_name : state.getDataStreamsList()) {
                  DataStream stream=new DataStream(handle,this,stream_name,true);
                  addDataStream(stream);
                }
              }
           catch (    InvalidProtocolBufferException e) {
                e.printStackTrace();
              }
          catch (    IOException e) {
                e.printStackTrace();
              }
            }
           else {
              setupObject();
            }
          }
          static ApplicationsState getInstance(Application app){
            synchronized (sLock) {
                if (sInstance == null) {
                  sInstance=new ApplicationsState(app);
                }
                return sInstance;
              }
            }
            public void startMyService(Application app){
                Log.d(TAG,"started");
                pm=ProviderManager.getInstance();
                sm=SlotManager.getInstance();
                String ns=Context.NOTIFICATION_SERVICE;
                mNotificationManager=(NotificationManager)getSystemService(ns);
                startUpdateTimer();
              }
       /** 
 * Registers given method with provided context and event.
 */
public void registerObserver(Context context,Object instance,Method method,Class event){
    if (!isEnabled())   return;
    if (context instanceof Application)   throw new RuntimeException("You may not register event handlers on the Application context");
    Map<Class<?>,Set<ObserverReference<?>>> methods=registrations.get(context);
    if (methods == null) {
      methods=new HashMap<Class<?>,Set<ObserverReference<?>>>();
      registrations.put(context,methods);
    }
    Set<ObserverReference<?>> observers=methods.get(event);
    if (observers == null) {
      observers=new HashSet<ObserverReference<?>>();
      methods.put(event,observers);
    }
    observers.add(new ObserverReference(instance,method));
  }
           