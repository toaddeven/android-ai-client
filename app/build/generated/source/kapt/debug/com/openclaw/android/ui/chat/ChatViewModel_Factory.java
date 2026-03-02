package com.openclaw.android.ui.chat;

import com.openclaw.android.data.gateway.GatewayConnection;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<GatewayConnection> gatewayProvider;

  public ChatViewModel_Factory(Provider<GatewayConnection> gatewayProvider) {
    this.gatewayProvider = gatewayProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(gatewayProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<GatewayConnection> gatewayProvider) {
    return new ChatViewModel_Factory(gatewayProvider);
  }

  public static ChatViewModel newInstance(GatewayConnection gateway) {
    return new ChatViewModel(gateway);
  }
}
