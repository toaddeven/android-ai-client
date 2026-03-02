package com.openclaw.android.ui.voice;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class VoiceViewModel_Factory implements Factory<VoiceViewModel> {
  private final Provider<Context> contextProvider;

  public VoiceViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public VoiceViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static VoiceViewModel_Factory create(Provider<Context> contextProvider) {
    return new VoiceViewModel_Factory(contextProvider);
  }

  public static VoiceViewModel newInstance(Context context) {
    return new VoiceViewModel(context);
  }
}
