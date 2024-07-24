package com.example.apppizzeria2.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apppizzeria2.Adapters.TermsAdapter;
import com.example.apppizzeria2.databinding.FragmentGalleryBinding;

import java.util.Arrays;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        List<String> terms = Arrays.asList(
                "1. Aceptación\nAl usar esta App de Pizzas de la Casa, aceptas estos términos y condiciones. Si no estás de acuerdo, no uses la App.",
                "2. Uso\nLa App es para hacer pedidos de pizza y otros productos de Pizzas de la Casa. No la uses para fines ilegales.",
                "3. Registro\nEs posible que necesites registrarte y proporcionar información personal. Eres responsable de tu cuenta y contraseña.",
                "4. Pedidos y Pagos\nAceptas pagar el precio total del pedido, incluidos impuestos y tarifas de entrega. Pizzas de la Casa puede rechazar o cancelar pedidos.",
                "5. Reembolsos\nLos reembolsos y cancelaciones están sujetos a la política de Pizzas de la Casa. Contacta a atención al cliente si tienes problemas con tu pedido.",
                "6. Propiedad Intelectual\nEl contenido de la App es propiedad de Pizzas de la Casa y está protegido por leyes de propiedad intelectual.",
                "7. Modificaciones\nPizzas de la Casa puede modificar estos términos en cualquier momento. Revisa los términos periódicamente.",
                "8. Responsabilidad\nPizzas de la Casa no es responsable de daños resultantes del uso de la App.",
                "9. Ley Aplicable\nEstos términos se rigen por las leyes de México. Cualquier disputa se resolverá en tribunales de México.",
                "10. Contacto\nSi tienes preguntas sobre estos términos, contacta a Pizzas de la Casa."
        );

        TermsAdapter adapter = new TermsAdapter(getContext(), terms);
        ListView listView = binding.listViewTerm;
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}