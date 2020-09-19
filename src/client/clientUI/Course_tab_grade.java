package client.clientUI;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Field;

public class Course_tab_grade {
    //学生成绩
    public Tab TabGrade() {
        Tab tab = new Tab();
        tab.setText("成绩查询");
        AnchorPane ap = new AnchorPane();

        //FilterableTreeItem rootNode = new FilterableTreeItem("2019-2020-3");


        tab.setContent(ap);
        return tab;
    }

//    //叶表根节点
//    public class FilterableTreeItem<T> extends TreeItem<T> {
//        final private ObservableList<TreeItem<T>> sourceList;
//        private FilteredList<TreeItem<T>> filteredList;
//        private ObjectProperty<TreeItemPredicate<T>> predicate = new SimpleObjectProperty<>();
//
//
//        public FilterableTreeItem(T value) {
//            super(value);
//            this.sourceList = FXCollections.observableArrayList();
//            this.filteredList = new FilteredList<>(this.sourceList);
//            this.filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
//                return child -> {
//                    // Set the predicate of child items to force filtering
//                    if (child instanceof FilterableTreeItem) {
//                        FilterableTreeItem<T> filterableChild = (FilterableTreeItem<T>) child;
//                        filterableChild.setPredicate(this.predicate.get());
//                    }
//                    // If there is no predicate, keep this tree item
//                    if (this.predicate.get() == null)
//                        return true;
//                    // If there are children, keep this tree item
//                    if (child.getChildren().size() > 0)
//                        return true;
//                    // Otherwise ask the TreeItemPredicate
//                    return this.predicate.get().test(this, child.getValue());
//                };
//            }, this.predicate));
//            setHiddenFieldChildren(this.filteredList);
//        }
//
//        protected void setHiddenFieldChildren(ObservableList<TreeItem<T>> list) {
//            try {
//                Field childrenField = TreeItem.class.getDeclaredField("children"); //$NON-NLS-1$
//                childrenField.setAccessible(true);
//                childrenField.set(this, list);
//
//                Field declaredField = TreeItem.class.getDeclaredField("childrenListener"); //$NON-NLS-1$
//                declaredField.setAccessible(true);
//                list.addListener((ListChangeListener<? super TreeItem<T>>) declaredField.get(this));
//            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//                throw new RuntimeException("Could not set TreeItem.children", e); //$NON-NLS-1$
//            }
//        }
//
//        public ObservableList<TreeItem<T>> getInternalChildren() {
//            return this.sourceList;
//        }
//
//        public void setPredicate(TreeItemPredicate<T> predicate) {
//            this.predicate.set(predicate);
//        }
//
//        public TreeItemPredicate getPredicate() {
//            return predicate.get();
//        }
//
//        public ObjectProperty<TreeItemPredicate<T>> predicateProperty() {
//            return predicate;
//        }
//    }
}
