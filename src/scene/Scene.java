package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * Scene class
 */
public class Scene {
    /**
     * name of scene
     */
    private String _name;
    /**
     * background in scene
     */
    private Color _backGround;
    /**
     * ambientLight in scene
     */
    private AmbientLight _ambientLight;
    /**
     * all the geometries in scene
     */
    private Geometries _geometries;
    /**
     * camera in scene
     */
    private Camera _camera;
    /**
     * the distance betwwen the camera and view
     */
    private double _distance;

    /**
     * scene constructor received the name scene
     * @param name - the scene name
     */
    public Scene(String name)
    {
        _name = name;
        _geometries = new Geometries();
    }
    public String get_name() {
        return _name;
    }

    public Color get_background() {
        return new Color(_backGround);
    }

    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    public Geometries get_geometries() {
        return _geometries;
    }

    public void set_background(Color _backGround) {
        this._backGround = _backGround;
    }

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public Camera get_camera() {
        return _camera;
    }

    public double get_distance() {
        return _distance;
    }

    public void addGeometries(Intersectable... geometries)
    {
        _geometries.add(geometries);
    }
}
