package com.openclaw.android.ui.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0007\b\t\n\u000bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "Canvas", "Chat", "Connect", "Settings", "Voice", "Lcom/openclaw/android/ui/navigation/Screen$Canvas;", "Lcom/openclaw/android/ui/navigation/Screen$Chat;", "Lcom/openclaw/android/ui/navigation/Screen$Connect;", "Lcom/openclaw/android/ui/navigation/Screen$Settings;", "Lcom/openclaw/android/ui/navigation/Screen$Voice;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen$Canvas;", "Lcom/openclaw/android/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Canvas extends com.openclaw.android.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.openclaw.android.ui.navigation.Screen.Canvas INSTANCE = null;
        
        private Canvas() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen$Chat;", "Lcom/openclaw/android/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Chat extends com.openclaw.android.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.openclaw.android.ui.navigation.Screen.Chat INSTANCE = null;
        
        private Chat() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen$Connect;", "Lcom/openclaw/android/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Connect extends com.openclaw.android.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.openclaw.android.ui.navigation.Screen.Connect INSTANCE = null;
        
        private Connect() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen$Settings;", "Lcom/openclaw/android/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Settings extends com.openclaw.android.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.openclaw.android.ui.navigation.Screen.Settings INSTANCE = null;
        
        private Settings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/openclaw/android/ui/navigation/Screen$Voice;", "Lcom/openclaw/android/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Voice extends com.openclaw.android.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.openclaw.android.ui.navigation.Screen.Voice INSTANCE = null;
        
        private Voice() {
        }
    }
}